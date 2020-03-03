package dvachmovie.activity.movie

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dvachmovie.R
import dvachmovie.architecture.Navigator
import dvachmovie.architecture.base.BaseActivity
import dvachmovie.architecture.logging.Logger
import dvachmovie.databinding.ActivityMovieBinding
import dvachmovie.di.core.ActivityComponent
import javax.inject.Inject

class MovieActivity : BaseActivity<MovieActivityVM,
        ActivityMovieBinding>(MovieActivityVM::class) {

    @Inject
    lateinit var logger: Logger

    override val layoutId = R.layout.activity_movie

    override fun inject(component: ActivityComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        viewModel.initCurrentBaseUrl()
    }

    override fun onSupportNavigateUp() =
            findNavController(this, R.id.navHostFragment).navigateUp()

    override fun onBackPressed() {
        val navController = findNavController(this, R.id.navHostFragment)

        when (navController.currentDestination?.label) {
            "MovieFragment" -> {
                Navigator(NavHostFragment.findNavController(supportFragmentManager
                        .primaryNavigationFragment!!), logger).navigateMovieToBackFragment()
            }
            "BackFragment" -> {
                Navigator(NavHostFragment.findNavController(supportFragmentManager
                        .primaryNavigationFragment!!), logger).navigateBackToMovieFragment()
            }
            "StartFragment" -> {
                finish()
            }
            "AloneMovieFragment" -> {
                finish()
            }
            else -> super.onBackPressed()
        }
    }
}
