package dvachmovie.activity.movie

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dvachmovie.R
import dvachmovie.base.BaseActivity
import dvachmovie.databinding.ActivityMovieBinding
import dvachmovie.di.core.ActivityComponent
import dvachmovie.fragment.back.BackFragmentDirections
import dvachmovie.fragment.movie.MovieFragmentDirections
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieActivity : BaseActivity<MovieActivityVM,
        ActivityMovieBinding>(MovieActivityVM::class.java) {

    override val layoutId = R.layout.activity_movie

    @Inject
    lateinit var movieTempRepository: MovieTempRepository

    override fun inject(component: ActivityComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onSupportNavigateUp() =
            findNavController(this, R.id.navHostFragment).navigateUp()

    override fun onBackPressed() {
        val navController = findNavController(this, R.id.navHostFragment)

        when (navController.currentDestination?.label) {
            "MovieFragment" -> {
                val direction = MovieFragmentDirections.ActionShowBackFragment()
                NavHostFragment.findNavController(supportFragmentManager.primaryNavigationFragment!!).navigate(direction)
            }
            "BackFragment" -> {
                val direction = BackFragmentDirections.ActionShowMovieFragment(movieTempRepository.currentMovie)
                NavHostFragment.findNavController(supportFragmentManager.primaryNavigationFragment!!).navigate(direction)
            }
            else -> super.onBackPressed()
        }
    }
}
