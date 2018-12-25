package dvachmovie.activity.start

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import dvachmovie.Constraints
import dvachmovie.R
import dvachmovie.activity.movie.MovieActivity
import dvachmovie.base.BaseActivity
import dvachmovie.databinding.ActivityStartBinding
import dvachmovie.di.core.ActivityComponent
import dvachmovie.repository.local.MovieRepository
import dvachmovie.usecase.DvachUseCase
import dvachmovie.usecase.InitWebm
import dvachmovie.usecase.SettingsUseCase
import dvachmovie.worker.WorkerManager
import javax.inject.Inject

class StartActivity : BaseActivity<StartActivityVM,
        ActivityStartBinding>(StartActivityVM::class.java) {

    override val layoutId = R.layout.activity_start

    @Inject
    lateinit var dvachUseCase: DvachUseCase

    @Inject
    lateinit var settingsUseCase: SettingsUseCase

    @Inject
    lateinit var movieRepository: MovieRepository

    override fun inject(component: ActivityComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        prepareData()
    }

    private fun prepareData() {
        movieRepository.observe(this, Observer { movies ->
            if (settingsUseCase.getLoadingParam() == Constraints.LOADING_EVERY_TIME ||
                    movies.size < 100) {
                dvachUseCase.getNumThreads("b", initWebm())
            } else {
                loadingMainActivity()
            }
        })
    }

    private fun initWebm(): InitWebm {
        return object : InitWebm {
            override fun initWebm() {
                WorkerManager.initDB()
                loadingMainActivity()
            }

            override fun countVideoUpdates(count: Int) {
                binding.progressLoadingSource.progress = count
            }

            override fun countVideoCalculatedSumm(summCount: Int) {
                binding.progressLoadingSource.max = summCount
            }
        }
    }

    private fun loadingMainActivity() {
        val intent = Intent(applicationContext,
                MovieActivity::class.java)
        startActivity(intent)
        finish()
    }
}