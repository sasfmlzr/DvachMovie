package dvachmovie.activity.movie

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dvachmovie.R
import dvachmovie.architecture.Navigator
import dvachmovie.architecture.base.BaseActivity
import dvachmovie.databinding.ActivityMovieBinding
import dvachmovie.di.core.ActivityComponent
import dvachmovie.repository.local.MovieDBCache
import dvachmovie.worker.WorkerManager
import javax.inject.Inject

class MovieActivity : BaseActivity<MovieActivityVM,
        ActivityMovieBinding>(MovieActivityVM::class) {

    @Inject
    lateinit var movieDBCache: MovieDBCache

    override val layoutId = R.layout.activity_movie

    override fun inject(component: ActivityComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        movieDBCache.movieList.observe(this, Observer {
            WorkerManager.initDB()
        })
    }

    override fun onSupportNavigateUp() =
            findNavController(this, R.id.navHostFragment).navigateUp()

    override fun onBackPressed() {
        val navController = findNavController(this, R.id.navHostFragment)

        when (navController.currentDestination?.label) {
            "MovieFragment" -> {
                Navigator(NavHostFragment.findNavController(supportFragmentManager
                        .primaryNavigationFragment!!)).navigateMovieToBackFragment()
            }
            "BackFragment" -> {
                Navigator(NavHostFragment.findNavController(supportFragmentManager
                        .primaryNavigationFragment!!)).navigateBackToMovieFragment()
            }
            else -> super.onBackPressed()
        }
    }
}
