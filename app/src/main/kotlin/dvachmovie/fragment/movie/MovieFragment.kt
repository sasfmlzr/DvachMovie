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
import androidx.core.view.isVisible
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
import com.dvachmovie.android.databinding.FragmentPreviewMoviesBinding
import dvachmovie.architecture.binding.BindingCache.pos
import dvachmovie.di.core.FragmentComponent
import dvachmovie.service.DownloadService
import dvachmovie.worker.WorkerManager

class MovieFragment : BaseFragment<MovieVM>(MovieVM::class), PermissionsCallback {

    private val routeToSettingsTask = { router.navigateMovieToSettingsFragment() }
    private val downloadBtnClicked = { runtimePermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE) }
    private lateinit var binding: FragmentMovieBinding
    private val copyURLTask = { movieUrl: String ->
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager
        clipboard.setPrimaryClip(ClipData
                .newPlainText("Copied Text", movieUrl))
        extensions.showMessage("URL video copied")!!
    }
    private val routeToPreviewTask = { router.navigateMovieToPreviewFragment() }

    private val showMessageTask = { message: String -> extensions.showMessage(message)!! }

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentMovieBinding.inflate(inflater, container, false)
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

        viewModel.cookie.observe(viewLifecycleOwner) { cookies ->
            BindingCache.cookie = cookies
            if (BindingCache.media.isNotEmpty()) {
                bindPlayer(binding.playerView)
            }
        }
        viewModel.uriMovies.observe(viewLifecycleOwner) { uri ->
            if (uri == null) return@observe
            if (uri.isNotEmpty()) {
                BindingCache.media = uri
                val player = binding.playerView
                (player.player as SimpleExoPlayer)
                if (!PlayerCache.isPrepared) {
                    bindPlayer(player)
                }
            }
        }
        viewModel.currentPos.observe(viewLifecycleOwner) { position ->
            pos = position
            val default = 0.toLong()
            if (pos.second == default) {
                binding.playerView.player?.seekToDefaultPosition(pos.first)
            } else {
                binding.playerView.player?.seekTo(pos.first, pos.second)
            }
        }

        viewModel.isPlayerControlVisibility.observe(viewLifecycleOwner) { isVisible ->
            binding.topButtonLayer.isVisible = isVisible
            binding.shuffleButton.isVisible = isVisible
            binding.downloadButton.isVisible = isVisible
            binding.settingsButton.isVisible = isVisible
            binding.copyURLButton.isVisible = isVisible
            binding.reportButton.isVisible = isVisible && viewModel.isReportBtnVisible.value == true
            binding.hideThreadButton.isVisible = isVisible && viewModel.isHideThreadBtnVisible.value == true
            binding.listVideosButton.isVisible = isVisible && viewModel.isListBtnVisible.value == true
        }
        viewModel.isReportBtnVisible.observe(viewLifecycleOwner) { isVisible ->
            binding.reportButton.isVisible = isVisible && viewModel.isPlayerControlVisibility.value == true
        }
        viewModel.isHideThreadBtnVisible.observe(viewLifecycleOwner) { isVisible ->
            binding.hideThreadButton.isVisible = isVisible && viewModel.isPlayerControlVisibility.value == true
        }
        viewModel.isListBtnVisible.observe(viewLifecycleOwner) { isVisible ->
            binding.listVideosButton.isVisible = isVisible && viewModel.isPlayerControlVisibility.value == true
        }
        binding.shuffleButton.setOnClickListener(viewModel.onBtnShuffleClicked)
        binding.downloadButton.setOnClickListener(viewModel.onBtnDownloadClicked)
        binding.settingsButton.setOnClickListener(viewModel.onBtnSettingsClicked)
        binding.copyURLButton.setOnClickListener(viewModel.onBtnCopyURLClicked)
        binding.reportButton.setOnClickListener(viewModel.onBtnReportClicked)
        binding.hideThreadButton.setOnClickListener(viewModel.onBtnHideThreadClicked)
        binding.listVideosButton.setOnClickListener(viewModel.onBtnListVideosClicked)
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
