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
import com.google.android.exoplayer2.ExoPlayer
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
import dvachmovie.service.DownloadService
import dvachmovie.utils.DirectoryHelper
import dvachmovie.worker.WorkerManager
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : BaseFragment<MovieVM,
        FragmentMovieBinding>(MovieVM::class), PermissionsCallback {

    private val routeToSettingsTask = { router.navigateMovieToSettingsFragment() }
    private val downloadBtnClicked = { runtimePermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE) }

    private val copyURLTask = { movieUrl: String ->
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager
        clipboard.primaryClip = ClipData
                .newPlainText("Copied Text", movieUrl)
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

        viewModel.currentMovie.observe(viewLifecycleOwner, Observer {
            if (it?.isPlayed == true) {
                WorkerManager.insertMovieInDB(context!!)
            }
        })

        viewModel.fillCurrentPos()

        viewModel.refreshVM()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayer(playerView)
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
                playerView.player.previous()
            }

            override fun onSwipeLeft() {
                playerView.player.next()
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
                if (playerView != null) {
                    viewModel.markMovieAsPlayed(playerView.player.currentPeriodIndex)

                    extensions.showMessage("Network error")

                    val player = (playerView.player as ExoPlayer)
                    player.retry()
                    player.next()
                }
            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?,
                                         trackSelections: TrackSelectionArray?) {
                var currentIndex = 0
                if (playerView != null) {
                    currentIndex = playerView.player.currentPeriodIndex
                }
                viewModel.markMovieAsPlayed(currentIndex)
            }
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

        viewModel.markMovieAsPlayed(index)

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
        viewModel.setCurrentMoviePipe.execute(
                viewModel.movieList.value?.get(playerView.player.currentWindowIndex)!!)

        downloadMovie(viewModel.currentMovie.value?.movieUrl
                ?: "", viewModel.cookie.value ?: "")
    }

    private fun downloadMovie(download: String, cookie: String) {
        DirectoryHelper.createDirectory(context!!)
        activity?.startService(DownloadService.getDownloadService(
                context!!,
                download,
                "${DirectoryHelper.ROOT_DIRECTORY_NAME}/",
                cookie))
    }
}
