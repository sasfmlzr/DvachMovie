package dvachmovie.activity.start

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dvachmovie.R
import dvachmovie.activity.movie.MovieActivity
import dvachmovie.databinding.StartActivityBinding
import dvachmovie.di.core.Injector
import dvachmovie.usecase.DvachUseCase
import dvachmovie.usecase.InitWebm
import javax.inject.Inject


class StartActivity : AppCompatActivity() {

    private lateinit var viewModel: StartActivityViewModel
    private lateinit var binding: StartActivityBinding

    @Inject
    lateinit var dvachUseCase: DvachUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.start_activity)
        viewModel = ViewModelProviders.of(this).get(StartActivityViewModel::class.java)

        binding.viewmodel = viewModel
        initDI()

        dvachUseCase.getNumThreads("b", initWebm())
    }

    private fun initDI() {
        Injector.navigationComponent().inject(this)
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