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
import dvachmovie.storage.local.MovieDBCache
import dvachmovie.usecase.base.CounterWebm
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.DvachModel
import dvachmovie.usecase.real.DvachUseCase
import dvachmovie.usecase.settingsStorage.GetBoardUseCase
import dvachmovie.usecase.settingsStorage.GetIsLoadingEveryTimeUseCase
import dvachmovie.usecase.settingsStorage.PutBoardUseCase
import dvachmovie.usecase.settingsStorage.PutCookieUseCase
import dvachmovie.utils.MovieObserver
import dvachmovie.worker.WorkerManager
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class StartFragment : BaseFragment<StartVM,
        FragmentStartBinding>(StartVM::class) {

    companion object {
        private const val MINIMUM_COUNT_MOVIES = 100
    }

    @Inject
    lateinit var dvachUseCase: DvachUseCase

    @Inject
    lateinit var movieObserver: MovieObserver

    @Inject
    lateinit var getIsLoadingEveryTimeUseCase: GetIsLoadingEveryTimeUseCase

    @Inject
    lateinit var getBoardUseCase: GetBoardUseCase

    @Inject
    lateinit var putCookieUseCase: PutCookieUseCase

    @Inject
    lateinit var putBoardUseCase: PutBoardUseCase

    override fun getLayoutId() = R.layout.fragment_start

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel

        scopeIO.launch(Job()) {
            putCookieUseCase.execute("92ea293bf47456479e25b11ba67bb17a")
        }
        viewModel.imageId.value = R.raw.cybermilosgif
        prepareData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonChangeDefaultBoard.setOnClickListener {
            scopeIO.launch(Job()) {
                putBoardUseCase.execute("b")
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
        runBlocking {
            movieObserver.observeDB(viewLifecycleOwner, Observer { movies ->
                scopeUI.launch {
                    if (getIsLoadingEveryTimeUseCase.execute(Unit) ||
                            movies.size < MINIMUM_COUNT_MOVIES) {
                        loadNewMovies()
                    } else {
                        router.navigateStartToMovieFragment()
                    }
                }
            })
        }
    }

    private fun loadNewMovies() {
        scopeIO.launch(Job()) {
            val inputModel = DvachUseCase.Params(getBoardUseCase.execute(Unit), counterWebm, executorResult)
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
            scopeUI.launch(Job()) {
                useCaseModel as DvachModel

                MovieDBCache.movieList = useCaseModel.movies
                WorkerManager.initDB()

                router.navigateStartToMovieFragment()
            }
        }

        override fun onFailure(t: Throwable) {
            extensions.showMessage(t.message!!)
            viewModel.viewRetryBtn.postValue(true)
        }
    }
}
