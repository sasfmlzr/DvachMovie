package dvachmovie.fragment.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dvachmovie.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentStartBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.utils.MovieObserver
import dvachmovie.worker.WorkerManager
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class StartFragment : BaseFragment<StartVM,
        FragmentStartBinding>(StartVM::class) {

    companion object {
        private const val MINIMUM_COUNT_MOVIES = 100
    }

    @Inject
    lateinit var movieObserver: MovieObserver

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
        runBlocking {
            movieObserver.observeDB(viewLifecycleOwner, Observer { movies ->
                if (movies.filter { !it.isPlayed }.size < MINIMUM_COUNT_MOVIES) {
                    viewModel.loadNewMovies()
                } else {
                    router.navigateStartToMovieFragment()
                }
            })
        }
    }
}
