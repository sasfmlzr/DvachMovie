package dvachmovie.fragment.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dvachmovie.R
import dvachmovie.architecture.ScopeProvider
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentStartBinding
import dvachmovie.db.data.Movie
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.utils.LocalMovieObserver.OnGetMovieListener
import dvachmovie.utils.MovieObserver
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
    lateinit var movieObserver: MovieObserver

    @Inject
    lateinit var scopeProvider: ScopeProvider

    override fun getLayoutId() = R.layout.fragment_start

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    private val routeToMovieFragmentTask = { router.navigateStartToMovieFragment() }
    private val showErrorTask = { throwable: Throwable ->
        extensions.showMessage(throwable.message ?: "Please try again")
        Unit
    }
    private val initDBTask = { WorkerManager.initDB(context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.routeToMovieFragmentTask = routeToMovieFragmentTask
        viewModel.showErrorTask = showErrorTask
        viewModel.initDBTask = initDBTask

        viewModel.imageId.value = R.raw.cybermilosgif
        prepareData()

        return binding.root
    }

    private fun prepareData() {
        scopeProvider.ioScope.launch(Job()) {
            movieObserver.observeDB(object : OnGetMovieListener {
                override fun onGet(movies: List<Movie>) {
                    logger.d("observe observeDB started")
                    if (movies.size < MINIMUM_COUNT_MOVIES ||
                            StartFragmentArgs.fromBundle(arguments!!).refreshMovies) {
                        viewModel.loadNewMovies()
                    } else {
                        router.navigateStartToMovieFragment()
                    }
                    logger.d("observe observeDB finished")
                }
            })
        }

    }
}
