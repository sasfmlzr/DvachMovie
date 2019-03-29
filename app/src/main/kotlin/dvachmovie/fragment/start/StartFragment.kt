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
import dvachmovie.repository.local.MovieDBCache
import dvachmovie.repository.local.MovieRepository
import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.*
import kotlinx.android.synthetic.main.fragment_start.*
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
    lateinit var movieRepository: MovieRepository

    @Inject
    lateinit var movieDBCache: MovieDBCache

    override var layoutId = R.layout.fragment_start

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel

        val value = "92ea293bf47456479e25b11ba67bb17a"
        settingsStorage.putCookie(value)
        viewModel.imageId.value = R.raw.cybermilosgif
        prepareData()

        return binding.root
    }

    //TODO: cookie factory for full version
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonChangeDefaultBoard.setOnClickListener {
            settingsStorage.putBoard("vg")
            activity?.recreate()
        }
        buttonRetry.setOnClickListener {
            viewModel.viewRetryBtn.value = false
            progressLoadingSource.progress = 0
            dvachUseCase.addParams(settingsStorage.getBoard(), counterWebm, executorResult).execute()
        }
    }

    private fun prepareData() {
        movieRepository.observeDB(viewLifecycleOwner, Observer { movies ->
            if (settingsStorage.isLoadingEveryTime() || movies.size < MINIMUM_COUNT_MOVIES) {
                dvachUseCase.addParams(settingsStorage.getBoard(), counterWebm, executorResult).execute()
            } else {
                router.navigateStartToMovieFragment()
            }
        })
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
            useCaseModel as DvachModel
            movieDBCache.movieList.value = useCaseModel.movies
            router.navigateStartToMovieFragment()
        }

        override fun onFailure(t: Throwable) {
            extensions.showMessage(t.message!!)
            viewModel.viewRetryBtn.value = true
        }
    }
}
