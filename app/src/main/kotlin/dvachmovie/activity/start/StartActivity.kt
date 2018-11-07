package dvachmovie.activity.start

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import dvachmovie.R
import dvachmovie.activity.main.MainActivity
import dvachmovie.databinding.StartActivityBinding
import dvachmovie.di.core.Injector


class StartActivity : AppCompatActivity() {

    private lateinit var viewModel: StartActivityViewModel
    private lateinit var binding: StartActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.start_activity)
        viewModel = ViewModelProviders.of(this).get(StartActivityViewModel::class.java)

        binding.viewmodel = viewModel
        initDI()
        //loadingMainActivity()
        //finish()
    }

    private fun loadingMainActivity(){
        val intent = Intent(applicationContext,
                MainActivity::class.java)
        startActivity(intent)
    }

    private fun initDI() {
        Injector.navigationComponent().inject(this)
    }
}