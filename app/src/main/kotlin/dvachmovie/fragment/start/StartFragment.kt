package dvachmovie.fragment.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dvachmovie.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentStartBinding
import dvachmovie.db.data.MovieEntity
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.storage.SettingsStorage
import dvachmovie.storage.local.MovieDBCache
import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.DvachModel
import dvachmovie.usecase.DvachUseCase
import dvachmovie.usecase.base.CounterWebm
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.utils.MovieObserver
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartFragment : BaseFragment<StartVM,
        FragmentStartBinding>(StartVM::class) {

    companion object {
        private const val MINIMUM_COUNT_MOVIES = 100
    }

    @Inject
    lateinit var dvachUseCase: DvachUseCase

    @Inject
    lateinit var settingsStorage: SettingsStorage

    @Inject
    lateinit var movieStorage: MovieStorage

    @Inject
    lateinit var movieObserver: MovieObserver

    @Inject
    lateinit var movieDBCache: MovieDBCache

    private val scopeIO = CoroutineScope(Dispatchers.IO)

    override fun getLayoutId() = R.layout.fragment_start

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.viewModel = viewModel

        val value = "92ea293bf47456479e25b11ba67bb17a"
        scopeIO.launch(Job()) {
            settingsStorage.putCookie(value)
        }
        viewModel.imageId.value = R.raw.cybermilosgif

        return binding.root
    }

    //TODO: cookie factory for full version
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieStorage.currentMovie.value = null

        if (movieStorage.movieIntentFilterEntity.value == null) {
            val uri = activity?.intent?.data
            if (uri != null) {
                if (uri.toString().contains(".webm")) {
                    val movie = listOf(MovieEntity(movieUrl = uri.toString()))
                    movieStorage.movieIntentFilterEntity.value = movie
                    executorResult.onSuccess(DvachModel(movie))
                } else {
                    executorResult.onFailure(RuntimeException("Uri unavailable"))
                }
            } else {
                prepareData()
            }
        } else {
            prepareData()
        }

        buttonChangeDefaultBoard.setOnClickListener {
            scopeIO.launch(Job()) {
                settingsStorage.putBoard(board = "b")
            }
            viewModel.viewRetryBtn.value = false
            progressLoadingSource.progress = 0
            loadNewMovies()
        }
        buttonRetry.setOnClickListener {
            viewModel.viewRetryBtn.value = false
            progressLoadingSource.progress = 0
            loadNewMovies()
        }
    }

    private fun prepareData() {
        movieObserver.observeDB(viewLifecycleOwner, Observer { movies ->
            if (settingsStorage.isLoadingEveryTime() || movies.size < MINIMUM_COUNT_MOVIES) {
                loadNewMovies()
            } else {
                router.navigateStartToMovieFragment()
            }
        })
    }

    private fun loadNewMovies() {
        scopeIO.launch(Job()) {
            val inputModel = DvachUseCase.Params(settingsStorage.getBoard(), counterWebm, executorResult)
            dvachUseCase.execute(inputModel)
        }
    }

    private val counterWebm = object : CounterWebm {
        override fun updateCurrentCountVideos(count: Int) {
            binding.progressLoadingSource.progress = count
        }

        override fun updateCountVideos(count: Int) {
            binding.progressLoadingSource.max = count
        }
    }

    private val executorResult = object : ExecutorResult {
        override fun onSuccess(useCaseModel: UseCaseModel) {
            GlobalScope.launch(Job()) {
                useCaseModel as DvachModel
                movieDBCache.movieList.postValue(useCaseModel.movies)
                router.navigateStartToMovieFragment()
            }
        }

        override fun onFailure(t: Throwable) {
            extensions.showMessage(t.message!!)
            viewModel.viewRetryBtn.postValue(true)
        }
    }
}
