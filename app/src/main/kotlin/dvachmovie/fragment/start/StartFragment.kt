package dvachmovie.fragment.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dvachmovie.Constants
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentStartBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.repository.local.MovieRepository
import dvachmovie.usecase.CounterWebm
import dvachmovie.usecase.DvachUseCase
import dvachmovie.usecase.ExecutorResult
import dvachmovie.usecase.SettingsUseCase
import dvachmovie.worker.WorkerManager
import javax.inject.Inject

class StartFragment : BaseFragment<StartVM,
        FragmentStartBinding>(StartVM::class.java) {

    @Inject
    lateinit var dvachUseCase: DvachUseCase

    @Inject
    lateinit var settingsUseCase: SettingsUseCase

    @Inject
    lateinit var movieRepository: MovieRepository

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentStartBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        prepareData()
        return binding.root
    }

    private fun prepareData() {
        movieRepository.observe(this, Observer { movies ->
            if (settingsUseCase.getBoolLoadingParam() == Constants.LOADING_EVERY_TIME ||
                    movies.size < 100) {
                dvachUseCase.execute("b", counterWebm, executorResult)
            } else {
                router.navigateStartToMovieFragment()
            }
        })
    }

    private val counterWebm = object : CounterWebm {
        override fun countVideoUpdates(count: Int) {
            binding.progressLoadingSource.progress = count
        }

        override fun countVideoCalculatedSumm(summCount: Int) {
            binding.progressLoadingSource.max = summCount
        }
    }

    private val executorResult = object : ExecutorResult {
        override fun onSuccess() {
            WorkerManager.initDB()
            router.navigateStartToMovieFragment()
        }

        override fun onFailure(t: Throwable) {
            extensions.showMessage(t.message!!)
        }
    }

}