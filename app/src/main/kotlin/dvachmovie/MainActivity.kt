package dvachmovie

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dvachmovie.databinding.MainActivityBinding
import dvachmovie.di.core.Injector
import dvachmovie.main.MainFragment
import dvachmovie.main.MoviesViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: MainActivityBinding

    private lateinit var dvachMovies: MutableList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDI()

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        binding.viewmodel = viewModel

        initMovies()

        binding.viewPager.adapter = MoviesViewPagerAdapter(dvachMovies, supportFragmentManager)

    }

    private fun initDI() {
        Injector.navigationComponent().inject(this)
    }

    private fun initMovies() {

        dvachMovies = mutableListOf(MainFragment.newInstance("https://2ch.hk/b/src/185160064/15401994992261.webm"),
                MainFragment.newInstance("https://2ch.hk/b/src/185159451/15402067914440.webm"),
                MainFragment.newInstance("https://2ch.hk/b/src/185165705/15402065817600.webm"))
    }

}
