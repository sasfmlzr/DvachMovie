package dvachmovie.fragment.movie

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import dvachmovie.moviestorage.GetIndexPosByMovieUseCase
import dvachmovie.service.DownloadService
import dvachmovie.storage.local.MovieDBCache
import dvachmovie.usecase.MarkCurrentMovieAsPlayedUseCase
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.ReportUseCase
import dvachmovie.usecase.settingsStorage.GetIsAllowGestureUseCase
import dvachmovie.usecase.settingsStorage.GetIsListBtnVisibleUseCase
import dvachmovie.usecase.settingsStorage.GetIsReportBtnVisibleUseCase
import dvachmovie.utils.DirectoryHelper
import dvachmovie.utils.MovieObserver
import dvachmovie.utils.MovieUtils
import dvachmovie.worker.WorkerManager
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class MovieBaseFragment : BaseFragment<MovieVM,
        FragmentMovieBinding>(MovieVM::class), PermissionsCallback {

    @Inject
    lateinit var movieObserver: MovieObserver

    @Inject
    lateinit var getIndexPosUseCase: GetIndexPosByMovieUseCase

    @Inject
    lateinit var isAllowGestureUseCase: GetIsAllowGestureUseCase

    @Inject
    lateinit var getIsListBtnVisibleUseCase: GetIsListBtnVisibleUseCase

    @Inject
    lateinit var getIsReportBtnVisibleUseCase: GetIsReportBtnVisibleUseCase

    @Inject
    lateinit var reportUseCase: ReportUseCase

    @Inject
    lateinit var movieUtils: MovieUtils

    @Inject
    lateinit var markCurrentMovieAsPlayedUseCase: MarkCurrentMovieAsPlayedUseCase

    private lateinit var ads: InterstitialAd

    protected abstract val containsAds: Boolean

    override fun getLayoutId() = R.layout.fragment_movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.currentMovie.observe(viewLifecycleOwner, Observer {
            if (it?.isPlayed == true) {
                WorkerManager.insertMovieInDB()
            }
        })

        scopeUI.launch {
            movieObserver.observeDB(viewLifecycleOwner)
        }

        if (viewModel.currentPos.value == Pair(0, 0L)) {
            viewModel.currentPos.value = try {
                Pair(getIndexPosUseCase.getIndexPosByMovie(viewModel.currentMovie.value), 0)
            } catch (e: Exception) {
                Pair(0, 0L)
            }
        }
        initAds()

        return binding.root
    }

    private fun initAds() {
        MobileAds.initialize(context,
                "ca-app-pub-3074235676525198~1117408577")
        ads = InterstitialAd(context)
        ads.adUnitId = "ca-app-pub-3074235676525198/4313459697"

        ads.adListener = object : AdListener() {
            override fun onAdOpened() {
                super.onAdOpened()
                playerView.player.playWhenReady = false
            }

            override fun onAdClosed() {
                super.onAdClosed()
                playerView.player.playWhenReady = true
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                ads.show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scopeUI.launch {
            viewModel.isGestureEnabled.value = isAllowGestureUseCase.execute(Unit)
        }

        initPlayer(playerView)
        configureButtons()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initPlayer(playerView: PlayerView) {
        playerView.player = ExoPlayerFactory.newSimpleInstance(playerView.context)
        viewModel.isGestureEnabled.observe(viewLifecycleOwner, Observer { isAllowGesture ->
            if (isAllowGesture) {
                playerView.setOnTouchListener(specificGestureListener)
            } else {
                playerView.setOnTouchListener(defaultGestureListener)
            }
        })
        playerView.player.addListener(playerListener)
    }

    private val specificGestureListener by lazy {
        object : OnSwipeTouchListener(context!!) {
            override fun onEventTouch(event: MotionEvent) {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    viewModel.isPlayerControlVisibility.value = toggleControlsVisibility()
                }
            }

            override fun onSwipeTop() {
                router.navigateMovieToPreviewFragment()
            }

            override fun onSwipeRight() {
                previous(playerView.player)

            }

            override fun onSwipeLeft() {
                next(playerView.player)
            }
        }
    }

    private val defaultGestureListener by lazy {
        object : OnSwipeTouchListener(context!!) {
            override fun onEventTouch(event: MotionEvent) {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    viewModel.isPlayerControlVisibility.value = toggleControlsVisibility()
                }
            }
        }
    }

    private fun toggleControlsVisibility() =
            if (playerView.isControllerVisible) {
                playerView.hideController()
                false
            } else {
                playerView.showController()
                true
            }

    private val playerListener by lazy {
        object : Player.EventListener {
            override fun onPlayerError(error: ExoPlaybackException?) {
                scopeUI.launch {
                    markCurrentMovieAsPlayedUseCase.execute(playerView.player.currentPeriodIndex)
                }

                MovieDBCache.movieList = listOf()
                viewModel.movieList.value = listOf()
                activity?.recreate()
            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?,
                                         trackSelections: TrackSelectionArray?) {
                var currentIndex = 0
                if (playerView != null) {
                    currentIndex = playerView.player.currentPeriodIndex
                }
                scopeUI.launch {
                    markCurrentMovieAsPlayedUseCase.execute(currentIndex)
                }
                if (containsAds) {
                    showAds()
                }
            }
        }
    }

    private fun showAds() {
        PlayerCache.countPlayed += 1
        if (PlayerCache.countPlayed % 30 == 0) {
            ads.loadAd(AdRequest.Builder().build())
        }
    }

    private fun configureButtons() {
        shuffleButton.setOnClickListener {
            viewModel.movieList.value =
                    movieUtils.shuffleMovies(viewModel.movieList.value
                            ?: listOf())
        }

        downloadButton.setOnClickListener {
            runtimePermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        settingsButton.setOnClickListener {
            router.navigateMovieToSettingsFragment()
        }

        copyURLButton.setOnClickListener {
            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE)
                    as ClipboardManager
            val clip = ClipData
                    .newPlainText("Copied Text", viewModel.currentMovie.value?.movieUrl)
            clipboard.primaryClip = clip
            extensions.showMessage("URL video copied")
        }

        listVideosButton.setOnClickListener {
            router.navigateMovieToPreviewFragment()
        }

        reportButton.setOnClickListener {
            val executorResult = object : ExecutorResult {
                override fun onSuccess(useCaseModel: UseCaseModel) {
                    extensions.showMessage("Report submitted!")
                    // extensions.showMessage((useCaseModel as DvachReportModel).message)
                }

                override fun onFailure(t: Throwable) {
                    extensions.showMessage("Something went wrong. Please try again")
                }
            }

            scopeUI.launch {
                val inputModel = ReportUseCase.Params(viewModel.currentMovie.value?.board!!,
                        viewModel.currentMovie.value?.thread!!,
                        viewModel.currentMovie.value?.post!!,
                        executorResult)
                reportUseCase.execute(inputModel)
            }
        }

        scopeUI.launch {
            viewModel.isReportBtnVisible.value = getIsReportBtnVisibleUseCase.execute(Unit)

            viewModel.isListBtnVisible.value = getIsListBtnVisibleUseCase.execute(Unit)
        }

    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    private fun initializePlayer() {
        playerView.player.playWhenReady = PlayerCache.shouldAutoPlay
        if (!PlayerCache.isPrepared) {
            bindPlayer(playerView)
            PlayerCache.isPrepared = true
        }
    }

    override fun onStop() {
        val index = playerView.player.currentPeriodIndex
        scopeUI.launch(Job()) {
            markCurrentMovieAsPlayedUseCase.execute(index)
        }
        releasePlayer()
        super.onStop()
    }

    private fun releasePlayer() {
        playerView.player.stop()
        updateStartPosition()
        PlayerCache.shouldAutoPlay = playerView.player.playWhenReady
    }

    private fun updateStartPosition() {
        viewModel.currentPos.value = Pair(playerView.player.currentWindowIndex,
                playerView.player.currentPosition)
        PlayerCache.isPrepared = false
    }

    override fun onPermissionsGranted(permissions: List<String>) {
        DirectoryHelper.createDirectory(context!!)
        viewModel.currentMovie.value =
                viewModel.movieList.value?.get(playerView.player.currentWindowIndex)
        activity?.startService(DownloadService.getDownloadService(
                context!!,
                viewModel.currentMovie.value?.movieUrl ?: "",
                DirectoryHelper.ROOT_DIRECTORY_NAME + "/"))
    }
}
