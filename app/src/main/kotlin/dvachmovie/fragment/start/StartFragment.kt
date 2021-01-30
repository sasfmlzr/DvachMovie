package dvachmovie.fragment.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import dvachmovie.AppConfig
import dvachmovie.R
import dvachmovie.architecture.ScopeProvider
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentStartBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.worker.WorkerManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartFragment : BaseFragment<StartVM,
        FragmentStartBinding>(StartVM::class) {

    companion object {
        private const val MINIMUM_COUNT_MOVIES = 100
    }

    @Inject
    lateinit var scopeProvider: ScopeProvider

    override fun getLayoutId() = R.layout.fragment_start

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    private val routeToMovieFragmentTask = { router.navigateStartToMovieFragment() }
    private val showErrorTask = { throwable: Throwable ->
        extensions.showMessage(throwable.message ?: "Please try again") ?: Unit
    }
    private lateinit var initDBTask: () -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel

        initDBTask = { WorkerManager.initDB(requireContext(), viewModel.getBoardPipe.execute(Unit)) }

        viewModel.routeToMovieFragmentTask = routeToMovieFragmentTask
        viewModel.showErrorTask = showErrorTask
        viewModel.initDBTask = initDBTask
        AppConfig.currentBaseUrl = viewModel.getCurrentBaseUrl()

        when (AppConfig.currentBaseUrl) {
            AppConfig.NEOCHAN_URL -> viewModel.imageId.value = R.raw.neochangif
            else -> viewModel.imageId.value = R.raw.cybermilosgif
        }

        initializePlayer()
        prepareData()

        return binding.root
    }

    private fun initializePlayer() {
        val player = SimpleExoPlayer.Builder(requireContext()).build()
        val uri = RawResourceDataSource.buildRawResourceUri(R.raw.samplevideo);
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(DefaultDataSourceFactory(requireContext(), "Exoplayer"))
                .createMediaSource(MediaItem.fromUri(uri))

        player.setMediaSource(mediaSource)
        player.prepare();
        player.playWhenReady = true;
        player.volume = 0f
        player.repeatMode = Player.REPEAT_MODE_ONE
        binding.player.player = player;
        binding.player.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM;
    }

    private fun prepareData() {
        scopeProvider.ioScope.launch(Job()) {
            val movies = viewModel.getMoviesFromDBByBoardPipe
                    .execute(Pair(viewModel.getBoardPipe.execute(Unit), AppConfig.currentBaseUrl))
            if (movies.size < MINIMUM_COUNT_MOVIES ||
                    StartFragmentArgs.fromBundle(requireArguments()).refreshMovies) {
                viewModel.loadNewMovies()
            } else {
                WorkerManager.fillCacheFromDB(requireContext(), viewModel.getBoardPipe.execute(Unit))
                router.navigateStartToMovieFragment()
            }
        }
    }

    override fun onDestroy() {
        binding.player.player?.release()
        super.onDestroy()
    }
}
