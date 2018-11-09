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

        val listMovies = intent.getStringArrayListExtra("url")
        initFragment(listMovies as MutableList<String>)
    }

    private fun initFragment(uriList: MutableList<String>) {
        supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance(uriList))
                .commit()
    }

    private fun initDI() {
        Injector.navigationComponent().inject(this)
    }

    /*private fun initMovies() {

        dvachMovies = mutableListOf("https://2ch.hk/b/src/185160064/15401994992261.webm",
                "https://2ch.hk/b/src/185159451/15402067914440.webm",
                "https://2ch.hk/b/src/185165705/15402065817600.webm")
    }*/

}
