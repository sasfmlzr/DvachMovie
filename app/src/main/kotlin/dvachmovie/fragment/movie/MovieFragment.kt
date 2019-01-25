package dvachmovie.fragment.movie

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import dvachmovie.WRITE_EXTERNAL_STORAGE_REQUEST_CODE
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.architecture.binding.PlayerViewBindingAdapter
import dvachmovie.architecture.listener.OnSwipeTouchListener
import dvachmovie.architecture.logging.Logger
import dvachmovie.databinding.FragmentMovieBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.repository.local.MovieRepository
import dvachmovie.service.DownloadService
import dvachmovie.utils.DirectoryHelper
import dvachmovie.worker.WorkerManager
import javax.inject.Inject

class MovieFragment : BaseFragment<MovieVM,
        FragmentMovieBinding>(MovieVM::class.java) {

    companion object {
        private const val TAG = "MovieFragment"
    }

    @Inject
    lateinit var movieRepository: MovieRepository

    @Inject
    lateinit var logger: Logger

    private lateinit var player: PlayerView

    override fun inject(component: FragmentComponent) = component.inject(this)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(viewLifecycleOwner)

        player = binding.playerView
        player.player = ExoPlayerFactory.newSimpleInstance(player.context)
        player.setOnTouchListener(onGestureListener(context!!))
        configurePlayer()
        configureButtons()

        movieRepository.getCurrent().observe(viewLifecycleOwner, Observer {
            if (it.isPlayed != 0) {
                WorkerManager.insertMovieInDB()
            }
        })

        movieRepository.observeDB(viewLifecycleOwner)

        viewModel.currentPos.value = Pair(movieRepository.getPos(), 0)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initializePlayer()
    }

    override fun onStop() {
        if (movieRepository.getMovies().value?.size != 0) {
            setUpCurrentMovie(1)
        }
        player.player.stop()
        super.onStop()

        releasePlayer()
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                DirectoryHelper.createDirectory(context!!)
            movieRepository.getCurrent().value =
                    movieRepository.getMovies().value!![player.player.currentWindowIndex]
            activity?.startService(DownloadService.getDownloadService(
                    context!!,
                    movieRepository.getCurrent().value!!.movieUrl,
                    DirectoryHelper.ROOT_DIRECTORY_NAME + "/"))
        }
    }

    private fun configurePlayer() {
        movieRepository.posPlayer = player.player.currentWindowIndex
        player.player.addListener(object : Player.EventListener {
            var idAddedToDB = false

            override fun onPlayerError(error: ExoPlaybackException?) {
                extensions.showMessage(error!!.cause?.localizedMessage!!)
                setUpCurrentMovie(1)
            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
                if (!idAddedToDB) {
                    val pos = player.player.currentWindowIndex

                    if (movieRepository.posPlayer == pos - 1) {
                        logger.d(TAG, "FORWARD POS ---------------------")
                    }

                    if (movieRepository.posPlayer == pos + 1) {
                        logger.d(TAG, "PREW POS ---------------------")
                    }

                    movieRepository.posPlayer = pos
                    setUpCurrentMovie(1)
                    idAddedToDB = true
                } else {
                    idAddedToDB = false
                }
            }
        })
    }

    private fun configureButtons() {
        binding.shuffleButton.setOnClickListener {
            movieRepository.getMovies().value =
                    movieRepository.shuffle(movieRepository.getMovies().value!!)
        }

        binding.downloadButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
            }
        }

        binding.settingsButton.setOnClickListener {
            router.navigateMovieToSettingsFragment()
        }
    }

    private fun setUpCurrentMovie(isPlayed: Int) {
        if (binding.viewModel!!.getUrlList().value!!.size != 0) {
            val movieUri = binding.viewModel!!.getUrlList().value!![player.player.currentPeriodIndex]
            movieUri.isPlayed = isPlayed
            movieRepository.isCalculateDiff = false
            movieRepository.getCurrent().value = movieUri
        }
    }

    //      ------------GESTURE LISTENER--------------
    private fun onGestureListener(context: Context) = object : OnSwipeTouchListener(context) {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN) {
                toggleControlsVisibility()
            }
            return super.onTouch(v, event)
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


    private var isPrerare = false
    private var playbackPosition: Long = 0

    private var shouldAutoPlay: Boolean = true

    private fun initializePlayer() {
        with(player.player!!) {
            playWhenReady = shouldAutoPlay
        }
        if (!isPrerare) {
            PlayerViewBindingAdapter.bindPlayer(player)
            isPrerare = true
        }
    }

    private fun releasePlayer() {
        if (player.player != null) {
            updateStartPosition()
            shouldAutoPlay = player.player!!.playWhenReady
        }
    }

    private fun updateStartPosition() {
        with(player.player!!) {
            playbackPosition = currentPosition
            movieRepository.posPlayer = currentWindowIndex
            viewModel.currentPos.value = Pair(currentWindowIndex, playbackPosition)
            playWhenReady = playWhenReady
            isPrerare = false
        }
    }
}