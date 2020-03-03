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
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
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
import kotlinx.android.synthetic.main.fragment_movie.*

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

        initPlayer(playerView)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initPlayer(playerView: PlayerView) {
        playerView.player = ExoPlayerFactory.newSimpleInstance(playerView.context)

        binding.playerView.findViewById<PlayerControlView>(R.id.exo_controller)
        val controlView = (playerView.children.filter { it is PlayerControlView }.first() as PlayerControlView)
        val buttonsGroup = ((controlView.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout).children.filter {
            it is ImageButton
        }.toList()
        val copyUrlDescription = resources.getString(R.string.copy_url_button)
        val downloadDescription = resources.getString(R.string.download)
        val copyUrlButton = buttonsGroup.filter {
            it.contentDescription == copyUrlDescription
        }.first()
        val downloadButton = buttonsGroup.filter {
            it.contentDescription == downloadDescription
        }.first()
        copyUrlButton.setOnClickListener(viewModel.onBtnCopyURLClicked)
        downloadButton.setOnClickListener(viewModel.onBtnDownloadClicked)

        viewModel.isGestureEnabled.observe(viewLifecycleOwner, Observer { isAllowGesture ->
            if (isAllowGesture) {
                playerView.setOnTouchListener(specificGestureListener)
            } else {
                playerView.setOnTouchListener(defaultGestureListener)
            }
        })

        playerView.setControllerVisibilityListener {
            viewModel.isPlayerControlVisibility.value = it == 0
        }

        playerView.player?.addListener(playerListener)

        binding.playerView.setOnFocusChangeListener { _, _ ->
            viewModel.isPlayerControlVisibility.value = playerView.isControllerVisible
        }

        binding.playerView.setOnClickListener {}
    }

    private val specificGestureListener by lazy {
        object : OnSwipeTouchListener(context!!) {
            override fun onEventTouch(event: MotionEvent) {}

            override fun onSwipeTop() {
                router.navigateMovieToPreviewFragment()
            }

            override fun onSwipeBottom() {
                viewModel.isPlayerControlVisibility.value = toggleControlsVisibility()
            }

            override fun onSwipeRight() {
                playerView.player?.previous()
            }

            override fun onSwipeLeft() {
                playerView.player?.next()
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

            override fun onPlayerError(error: ExoPlaybackException) {
                if (playerView != null) {

                    if (error.message != "java.lang.IllegalArgumentException") {
                        extensions.showMessage("Network error")
                    } else {
                        logger.d("Internal error")
                    }

                    val player = (playerView.player as ExoPlayer)
                    player.retry()
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
        playerView.player?.playWhenReady = PlayerCache.shouldAutoPlay
        if (!PlayerCache.isPrepared) {
            bindPlayer(playerView)
            PlayerCache.isPrepared = true
        }
    }

    override fun onStop() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        stopPlayer()
        super.onStop()
    }

    private fun stopPlayer() {
        playerView.player?.playWhenReady = false
        PlayerCache.shouldAutoPlay = playerView?.player?.playWhenReady ?: false
    }

    override fun onPermissionsGranted(permissions: List<String>) {
        if (permissions.isNotEmpty()) {
            downloadMovie(viewModel.currentMovie.value
                    ?: "", viewModel.cookie.value ?: "")
        }
    }

    private fun downloadMovie(download: String, cookie: String) {
        activity?.startService(DownloadService.getDownloadService(
                context!!,
                download,
                Environment.DIRECTORY_DOWNLOADS,
                cookie))
    }
}
