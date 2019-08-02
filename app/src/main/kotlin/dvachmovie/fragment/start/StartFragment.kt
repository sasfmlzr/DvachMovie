package dvachmovie.fragment.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        extensions.showMessage(throwable.message ?: "Please try again")
        Unit
    }
    private lateinit var initDBTask: () -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel

        initDBTask = { WorkerManager.initDB(context!!, viewModel.getBoardPipe.execute(Unit)) }

        viewModel.routeToMovieFragmentTask = routeToMovieFragmentTask
        viewModel.showErrorTask = showErrorTask
        viewModel.initDBTask = initDBTask

        viewModel.imageId.value = R.raw.cybermilosgif
        prepareData()

        return binding.root
    }

    private fun prepareData() {
        scopeProvider.ioScope.launch(Job()) {
            AppConfig.currentBaseUrl = viewModel.getCurrentBaseUrl()
            val movies = viewModel.getMoviesFromDBByBoardPipe
                    .execute(Pair(viewModel.getBoardPipe.execute(Unit), AppConfig.currentBaseUrl))
            if (movies.size < MINIMUM_COUNT_MOVIES ||
                    StartFragmentArgs.fromBundle(arguments!!).refreshMovies) {
                viewModel.loadNewMovies()
            } else {
                WorkerManager.fillCacheFromDB(context!!, viewModel.getBoardPipe.execute(Unit))
                router.navigateStartToMovieFragment()
            }
        }
    }
}
