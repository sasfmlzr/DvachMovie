package dvachmovie.activity.movie

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.dvachmovie.android.R
import dvachmovie.architecture.Navigator
import dvachmovie.architecture.base.BaseActivity
import dvachmovie.architecture.logging.Logger
import com.dvachmovie.android.databinding.ActivityMovieBinding
import dvachmovie.di.core.ActivityComponent
import javax.inject.Inject

class MovieActivity : BaseActivity<MovieActivityVM>(MovieActivityVM::class) {

    @Inject
    lateinit var logger: Logger

    override fun inject(component: ActivityComponent) = component.inject(this)

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
