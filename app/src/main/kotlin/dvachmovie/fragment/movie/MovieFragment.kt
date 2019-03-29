package dvachmovie.fragment.movie

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlaybackException.*
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import dvachmovie.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.architecture.base.PermissionsCallback
import dvachmovie.architecture.binding.bindPlayer
import dvachmovie.architecture.listener.OnSwipeTouchListener
import dvachmovie.databinding.FragmentMovieBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.repository.local.MovieDBCache
import dvachmovie.repository.local.MovieRepository
import dvachmovie.repository.local.MovieStorage
import dvachmovie.service.DownloadService
import dvachmovie.utils.DirectoryHelper
import dvachmovie.worker.WorkerManager
import javax.inject.Inject

class MovieFragment : BaseFragment<MovieVM,
        FragmentMovieBinding>(MovieVM::class), PermissionsCallback {

    @Inject
    lateinit var movieRepository: MovieRepository
    @Inject
    lateinit var movieStorage: MovieStorage
    @Inject
    lateinit var movieCaches: MovieDBCache

    private lateinit var player: PlayerView

    override var layoutId = R.layout.fragment_movie

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel
        initPlayer()
        configurePlayer()
        configureButtons()

        movieStorage.currentMovie.observe(viewLifecycleOwner, Observer {
            if (it.isPlayed) {
                WorkerManager.insertMovieInDB()
            }
        })

        movieRepository.observeDB(viewLifecycleOwner)

        viewModel.currentPos.value = Pair(movieStorage.getIndexPosition(), 0)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initializePlayer()
    }

    override fun onStop() {
        if (movieStorage.movieList.value?.isNotEmpty() == true) {
            setUpCurrentMovie(true)
        }
        player.player.stop()
        super.onStop()

        releasePlayer()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initPlayer() {
        player = binding.playerView
        player.player = ExoPlayerFactory.newSimpleInstance(player.context)
        player.setOnTouchListener(onGestureListener(context!!))
    }

    private fun configurePlayer() {
        player.player.addListener(object : Player.EventListener {
            override fun onPlayerError(error: ExoPlaybackException?) {
                var errorMessage = String()
                when (error?.type) {
                    TYPE_SOURCE -> errorMessage = "Source error"
                    TYPE_RENDERER -> errorMessage = "Render error"
                    TYPE_UNEXPECTED -> errorMessage = "Unexpected error"
                }
                if (error?.cause?.localizedMessage != null) {
                    errorMessage = "$errorMessage - ${error.cause?.localizedMessage}"
                }
                extensions.showMessage(errorMessage)
                setUpCurrentMovie(true)

                movieCaches.movieList.value = mutableListOf()
                movieStorage.movieList.value = mutableListOf()
                activity?.recreate()
            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?,
                                         trackSelections: TrackSelectionArray?) {
                setUpCurrentMovie(true)
            }
        })
    }

    private fun configureButtons() {
        binding.shuffleButton.setOnClickListener {
            movieRepository.shuffleMovies()
        }

        binding.downloadButton.setOnClickListener {
            runtimePermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        binding.settingsButton.setOnClickListener {
            router.navigateMovieToSettingsFragment()
        }
    }

    private fun setUpCurrentMovie(isPlayed: Boolean) {
        if (movieStorage.movieList.value?.isNotEmpty() == true) {
            val movieUri =
                    movieStorage.movieList.value?.get(player.player.currentPeriodIndex)
            movieUri?.isPlayed = isPlayed
            movieStorage.currentMovie.value = movieUri
        }
    }

    //      ------------GESTURE LISTENER--------------
    private fun onGestureListener(context: Context) = object : OnSwipeTouchListener(context) {
        override fun onEventTouch(event: MotionEvent) {
            if (event.action == MotionEvent.ACTION_DOWN) {
                toggleControlsVisibility()
            }
        }

        override fun onSwipeTop() {
            router.navigateMovieToPreviewFragment()
        }
    }

    private fun toggleControlsVisibility() {
        if (player.isControllerVisible) {
            viewModel.isPlayerControlVisibility.value = false
            player.hideController()
        } else {
            viewModel.isPlayerControlVisibility.value = true
            player.showController()
        }
    }
    //      ------------GESTURE LISTENER--------------

    private var isPrepared = false
    private var playbackPosition: Long = 0

    private var shouldAutoPlay: Boolean = true

    private fun initializePlayer() {
        with(player.player) {
            playWhenReady = shouldAutoPlay
        }
        if (!isPrepared) {
            bindPlayer(player)
            isPrepared = true
        }
    }

    private fun releasePlayer() {
        with(player.player) {
            updateStartPosition(this)
            shouldAutoPlay = this.playWhenReady
        }
    }

    private fun updateStartPosition(player: Player) {
        with(player) {
            playbackPosition = currentPosition
            viewModel.currentPos.value = Pair(currentWindowIndex, playbackPosition)
            playWhenReady = playWhenReady
            isPrepared = false
        }
    }

    override fun onPermissionsGranted(permissions: List<String>) {
        DirectoryHelper.createDirectory(context!!)
        movieStorage.currentMovie.value =
                movieStorage.movieList.value?.get(player.player.currentWindowIndex)
        activity?.startService(DownloadService.getDownloadService(
                context!!,
                movieStorage.currentMovie.value?.movieUrl ?: "",
                DirectoryHelper.ROOT_DIRECTORY_NAME + "/"))
    }
}
