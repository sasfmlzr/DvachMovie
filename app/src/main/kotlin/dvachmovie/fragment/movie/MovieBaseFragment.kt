package dvachmovie.fragment.movie

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import dvachmovie.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.architecture.base.PermissionsCallback
import dvachmovie.architecture.binding.bindPlayer
import dvachmovie.architecture.listener.OnSwipeTouchListener
import dvachmovie.databinding.FragmentMovieBinding
import dvachmovie.fragment.movie.PlayerCache.countPlayed
import dvachmovie.repository.local.MovieDBCache
import dvachmovie.repository.local.MovieRepository
import dvachmovie.repository.local.MovieStorage
import dvachmovie.service.DownloadService
import dvachmovie.utils.DirectoryHelper
import dvachmovie.worker.WorkerManager
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Inject

abstract class MovieBaseFragment : BaseFragment<MovieVM,
        FragmentMovieBinding>(MovieVM::class), PermissionsCallback {

    @Inject
    lateinit var movieRepository: MovieRepository
    @Inject
    lateinit var movieStorage: MovieStorage
    @Inject
    lateinit var movieCaches: MovieDBCache

    private lateinit var ads: InterstitialAd

    protected abstract val containsAds: Boolean

    override fun getLayoutId() = R.layout.fragment_movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel

        movieStorage.currentMovie.observe(viewLifecycleOwner, Observer {
            if (it.isPlayed) {
                WorkerManager.insertMovieInDB()
            }
        })

        movieRepository.observeDB(viewLifecycleOwner)

        if (viewModel.currentPos.value == Pair(0, 0L)) {
            viewModel.currentPos.value = Pair(movieStorage.getIndexPosition(), 0)
        }

        MobileAds.initialize(context,
                "ca-app-pub-3074235676525198~3986251123")
        ads = InterstitialAd(context)
        ads.adUnitId = "ca-app-pub-3074235676525198/2334198033"

        ads.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                ads.show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayer(playerView)
        configureButtons()
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        if (movieStorage.movieList.value?.isNotEmpty() == true) {
            movieRepository.markCurrentMovieAsPlayed(true, playerView.player.currentPeriodIndex)
        }
        playerView.player.stop()
        super.onStop()

        releasePlayer()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initPlayer(playerView: PlayerView) {
        playerView.player = ExoPlayerFactory.newSimpleInstance(playerView.context)
        playerView.setOnTouchListener(gestureListener)

        playerView.player.addListener(object : Player.EventListener {
            override fun onPlayerError(error: ExoPlaybackException?) {
                movieRepository.markCurrentMovieAsPlayed(true, playerView.player.currentPeriodIndex)

                movieCaches.movieList.value = mutableListOf()
                movieStorage.movieList.value = mutableListOf()
                activity?.recreate()
            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?,
                                         trackSelections: TrackSelectionArray?) {
                movieRepository.markCurrentMovieAsPlayed(true, playerView.player.currentPeriodIndex)

                if (containsAds) {
                    countPlayed += 1
                    if (countPlayed % 10 == 0) {
                        showAd()
                    }
                }
            }
        })
    }

    private fun configureButtons() {
        shuffleButton.setOnClickListener {
            movieRepository.shuffleMovies()
        }

        downloadButton.setOnClickListener {
            runtimePermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        settingsButton.setOnClickListener {
            router.navigateMovieToSettingsFragment()
        }
    }

    private val gestureListener by lazy {
        object : OnSwipeTouchListener(context!!) {
            override fun onEventTouch(event: MotionEvent) {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    toggleControlsVisibility()
                }
            }

            override fun onSwipeTop() {
                router.navigateMovieToPreviewFragment()
            }
        }
    }

    private fun toggleControlsVisibility() {
        if (playerView.isControllerVisible) {
            viewModel.isPlayerControlVisibility.value = false
            playerView.hideController()
        } else {
            viewModel.isPlayerControlVisibility.value = true
            playerView.showController()
        }
    }

    private fun initializePlayer() {
        playerView.player.playWhenReady = PlayerCache.shouldAutoPlay
        if (!PlayerCache.isPrepared) {
            bindPlayer(playerView)
            PlayerCache.isPrepared = true
        }
    }

    private fun releasePlayer() {
        updateStartPosition()
        PlayerCache.shouldAutoPlay = playerView.player.playWhenReady
    }

    private fun updateStartPosition() {
        PlayerCache.playbackPosition = playerView.player.currentPosition
        viewModel.currentPos.value = Pair(playerView.player.currentWindowIndex, PlayerCache.playbackPosition)
        PlayerCache.isPrepared = false
    }

    override fun onPermissionsGranted(permissions: List<String>) {
        DirectoryHelper.createDirectory(context!!)
        movieStorage.currentMovie.value =
                movieStorage.movieList.value?.get(playerView.player.currentWindowIndex)
        activity?.startService(DownloadService.getDownloadService(
                context!!,
                movieStorage.currentMovie.value?.movieUrl ?: "",
                DirectoryHelper.ROOT_DIRECTORY_NAME + "/"))
    }

    private fun showAd() {
        ads.loadAd(AdRequest.Builder().build())
    }
}
