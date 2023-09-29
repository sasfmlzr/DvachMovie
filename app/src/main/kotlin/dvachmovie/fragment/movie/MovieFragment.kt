package dvachmovie.fragment.movie

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.PlayerView
import dvachmovie.AppConfig
import com.dvachmovie.android.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.architecture.base.PermissionsCallback
import dvachmovie.architecture.binding.BindingCache
import dvachmovie.architecture.binding.bindPlayer
import dvachmovie.architecture.listener.OnSwipeTouchListener
import com.dvachmovie.android.databinding.FragmentMovieBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.service.DownloadService
import dvachmovie.worker.WorkerManager

class MovieFragment : BaseFragment<MovieVM,
        FragmentMovieBinding>(MovieVM::class), PermissionsCallback {

    private val routeToSettingsTask = { router.navigateMovieToSettingsFragment() }
    private val downloadBtnClicked = { runtimePermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE) }

    private val copyURLTask = { movieUrl: String ->
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager
        clipboard.setPrimaryClip(ClipData
                .newPlainText("Copied Text", movieUrl))
        extensions.showMessage("URL video copied")!!
    }
    private val routeToPreviewTask = { router.navigateMovieToPreviewFragment() }

    private val showMessageTask = { message: String -> extensions.showMessage(message)!! }

    override fun getLayoutId() = R.layout.fragment_movie

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.downloadBtnClicked = downloadBtnClicked
        viewModel.routeToSettingsTask = routeToSettingsTask
        viewModel.copyURLTask = copyURLTask
        viewModel.routeToPreviewTask = routeToPreviewTask
        viewModel.showMessageTask = showMessageTask

        PlayerCache.shouldAutoPlay = true

        viewModel.currentMovie.observe(viewLifecycleOwner) {
            if (it?.isPlayed == true) {
                WorkerManager.insertMovieInDB(requireContext())
            }

            if (PlayerCache.isHideMovieByThreadTask) {
                (binding.playerView.player as ExoPlayer).release()
                router.navigateMovieToSelf()
                PlayerCache.isHideMovieByThreadTask = false
            }
        }

        viewModel.fillCurrentPos()

        viewModel.refreshVM()

        when (viewModel.getCurrentBaseUrl()) {
            AppConfig.FOURCHAN_URL -> {
                viewModel.isReportBtnVisible.value = false
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayer(binding.playerView)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initPlayer(playerView: PlayerView) {
        playerView.player = SimpleExoPlayer.Builder(playerView.context)
                .build()
        viewModel.isGestureEnabled.observe(viewLifecycleOwner) { isAllowGesture ->
            if (isAllowGesture) {
                playerView.setOnTouchListener(specificGestureListener)
            } else {
                playerView.setOnTouchListener(defaultGestureListener)
            }
        }

        playerView.setControllerVisibilityListener {
            viewModel.isPlayerControlVisibility.value = it == 0
        }

        (playerView.player as ExoPlayer).addListener(playerListener)

        binding.playerView.setOnFocusChangeListener { _, _ ->
            viewModel.isPlayerControlVisibility.value = playerView.isControllerVisible
        }

        binding.playerView.setOnClickListener {}
    }

    private val specificGestureListener by lazy {
        object : OnSwipeTouchListener(requireContext()) {
            override fun onEventTouch(event: MotionEvent) = Unit

            override fun onSwipeTop() {
                router.navigateMovieToPreviewFragment()
            }

            override fun onSwipeBottom() {
                viewModel.isPlayerControlVisibility.value = toggleControlsVisibility()
            }

            override fun onSwipeRight() {
                binding.playerView.player?.previous()
            }

            override fun onSwipeLeft() {
                binding.playerView.player?.next()
            }
        }
    }

    private val defaultGestureListener by lazy {
        object : OnSwipeTouchListener(requireContext()) {
            override fun onEventTouch(event: MotionEvent) {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    viewModel.isPlayerControlVisibility.value = toggleControlsVisibility()
                }
            }
        }
    }

    private fun toggleControlsVisibility() =
            if (binding.playerView.isControllerVisible) {
                binding.playerView.hideController()
                false
            } else {
                binding.playerView.showController()
                true
            }

    private val playerListener by lazy {
        object : Player.Listener {

            override fun onPlayerError(error: PlaybackException) {
                viewModel.markMovieAsPlayed(binding.playerView.player?.currentPeriodIndex ?: 0)

                if (error.message != "java.lang.IllegalArgumentException") {
                    extensions.showMessage("Network error")
                } else {
                    logger.d("Internal error")
                    error.printStackTrace()
                }

                (binding.playerView.player as ExoPlayer).let {
                    it.prepare()
                    it.next()
                }
                super.onPlayerError(error)
            }

            override fun onTracksChanged(tracks: Tracks) {
                val currentIndex: Int = binding.playerView.player?.currentPeriodIndex ?: 0
                viewModel.markMovieAsPlayed(currentIndex)
                super.onTracksChanged(tracks)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    private fun initializePlayer() {
        binding.playerView.player?.playWhenReady = PlayerCache.shouldAutoPlay
        if (!PlayerCache.isPrepared && BindingCache.media.isNotEmpty()) {
            bindPlayer(binding.playerView)
            PlayerCache.isPrepared = true
        }
    }

    override fun onStop() {
        val index = binding.playerView.player?.currentPeriodIndex ?: 0

        viewModel.markMovieAsPlayed(index)

        stopPlayer()
        super.onStop()
    }

    private fun stopPlayer() {
        updateStartPosition()
        PlayerCache.shouldAutoPlay = binding.playerView.player?.playWhenReady ?: false
        binding.playerView.player?.playWhenReady = false
    }

    private fun updateStartPosition() {
        viewModel.currentPos.value = Pair(binding.playerView.player?.currentWindowIndex ?: 0,
                binding.playerView.player?.currentPosition ?: 0)
    }

    override fun onDestroyView() {
        PlayerCache.isPrepared = false
        binding.playerView.player?.release()
        super.onDestroyView()
    }

    override fun onPermissionsGranted(permissions: List<String>) {
        if (permissions.isNotEmpty()) {
            viewModel.setCurrentMoviePipe.execute(
                    viewModel.movieList.value?.get(binding.playerView.player?.currentWindowIndex
                            ?: 0)!!)

            downloadMovie(viewModel.currentMovie.value?.movieUrl
                    .orEmpty(), viewModel.cookie.value.orEmpty())
        }
    }

    private fun downloadMovie(download: String, cookie: String) {
        activity?.startService(DownloadService.getDownloadService(
                requireContext(),
                download,
                Environment.DIRECTORY_DOWNLOADS,
                cookie))
    }
}
