package dvachmovie.activity.main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dvachmovie.R
import dvachmovie.databinding.MainActivityBinding
import dvachmovie.di.core.Injector
import dvachmovie.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDI()

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        binding.viewmodel = viewModel

        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment())
                .commit()
    }

    private fun initDI() {
        Injector.navigationComponent().inject(this)
    }
}
