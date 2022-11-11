package dvachmovie.fragment.alonemovie

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.view.children
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import dvachmovie.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.architecture.base.PermissionsCallback
import dvachmovie.architecture.binding.bindPlayer
import dvachmovie.architecture.listener.OnSwipeTouchListener
import dvachmovie.databinding.FragmentAloneMovieBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.fragment.movie.PlayerCache
import dvachmovie.service.DownloadService

class AloneMovieFragment : BaseFragment<AloneMovieVM,
        FragmentAloneMovieBinding>(AloneMovieVM::class), PermissionsCallback {

    private val downloadBtnClicked = { runtimePermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE) }

    private val copyURLTask = { movieUrl: String ->
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager
        clipboard.setPrimaryClip(ClipData
                .newPlainText("Copied Text", movieUrl))
        extensions.showMessage("URL video copied")!!
    }

    private val showMessageTask = { message: String -> extensions.showMessage(message)!! }

    private var url = ""

    override fun getLayoutId() = R.layout.fragment_alone_movie

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        super.onCreateView(inflater, container, savedInstanceState)

        url = (arguments?.get("android-support-nav:controller:deepLinkIntent") as Intent).data.toString()
        viewModel.getUrlTask = { url }

        binding.viewModel = viewModel

        viewModel.downloadBtnClicked = downloadBtnClicked
        viewModel.copyURLTask = copyURLTask
        viewModel.showMessageTask = showMessageTask

        PlayerCache.shouldAutoPlay = true

        viewModel.fillCurrentPos()

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

        binding.playerView.findViewById<PlayerControlView>(R.id.exo_controller)
        val controlView = playerView.children.filter { it is PlayerControlView }.first() as PlayerControlView
        val buttonsGroup = ((controlView.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout).children.filter {
            it is ImageButton
        }.toList()
        val copyUrlDescription = resources.getString(R.string.copy_url_button)
        val downloadDescription = resources.getString(R.string.download)
        val copyUrlButton = buttonsGroup.first {
            it.contentDescription == copyUrlDescription
        }
        val downloadButton = buttonsGroup.first {
            it.contentDescription == downloadDescription
        }
        copyUrlButton.setOnClickListener(viewModel.onBtnCopyURLClicked)
        downloadButton.setOnClickListener(viewModel.onBtnDownloadClicked)

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

                if (error.message != "java.lang.IllegalArgumentException") {
                    extensions.showMessage("Network error")
                } else {
                    logger.d("Internal error")
                }

                (binding.playerView.player as ExoPlayer).apply {
                    prepare()
                }
                super.onPlayerError(error)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    private fun initializePlayer() {
        binding.playerView.player?.playWhenReady = PlayerCache.shouldAutoPlay
        if (!PlayerCache.isPrepared) {
            bindPlayer(binding.playerView)
            PlayerCache.isPrepared = true
        }
    }

    override fun onStop() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        stopPlayer()
        super.onStop()
    }

    private fun stopPlayer() {
        binding.playerView.player?.playWhenReady = false
        PlayerCache.shouldAutoPlay = binding.playerView.player?.playWhenReady ?: false
    }

    override fun onPermissionsGranted(permissions: List<String>) {
        if (permissions.isNotEmpty()) {
            downloadMovie(viewModel.currentMovie.value
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
