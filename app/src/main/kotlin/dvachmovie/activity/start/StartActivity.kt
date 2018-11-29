package dvachmovie.activity.start

import android.content.Intent
import android.os.Bundle
import dvachmovie.R
import dvachmovie.activity.movie.MovieActivity
import dvachmovie.base.BaseActivity
import dvachmovie.databinding.ActivityStartBinding
import dvachmovie.di.core.ActivityComponent
import dvachmovie.usecase.DvachUseCase
import dvachmovie.usecase.InitWebm
import javax.inject.Inject

class StartActivity : BaseActivity<StartActivityVM,
        ActivityStartBinding>(StartActivityVM::class.java) {

    override val layoutId = R.layout.activity_start

    @Inject
    lateinit var dvachUseCase: DvachUseCase

    override fun inject(component: ActivityComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        dvachUseCase.getNumThreads("b", initWebm())
    }

    private fun initWebm(): InitWebm {
        return object : InitWebm {
            override fun initWebm() {
                loadingMainActivity()
                finish()
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
    }
}