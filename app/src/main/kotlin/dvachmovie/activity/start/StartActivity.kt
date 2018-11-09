package dvachmovie.activity.start

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dvachmovie.R
import dvachmovie.activity.main.MainActivity
import dvachmovie.databinding.StartActivityBinding
import dvachmovie.di.core.Injector
import dvachmovie.usecase.DvachUseCase
import dvachmovie.usecase.InitWebm
import java.util.*


class StartActivity : AppCompatActivity() {

    private lateinit var viewModel: StartActivityViewModel
    private lateinit var binding: StartActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.start_activity)
        viewModel = ViewModelProviders.of(this).get(StartActivityViewModel::class.java)

        binding.viewmodel = viewModel
        initDI()

        val dvachUseCase = DvachUseCase(initWebm())
        dvachUseCase.getNumThreads("b")
    }

    private fun initDI() {
        Injector.navigationComponent().inject(this)
    }


    private fun initWebm(): InitWebm {
        return object : InitWebm {
            override fun initWebm(listMovies: List<String>) {
                loadingMainActivity(listMovies)
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

    private fun loadingMainActivity(listMovies: List<String>) {
        val intent = Intent(applicationContext,
                MainActivity::class.java)
        val bundle = Bundle()
        bundle.putStringArrayList("url", listMovies as ArrayList<String>)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}