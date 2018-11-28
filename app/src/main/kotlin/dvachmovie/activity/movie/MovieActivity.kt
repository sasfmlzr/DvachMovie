package dvachmovie.activity.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dvachmovie.R
import dvachmovie.databinding.ActivityMovieBinding
import dvachmovie.di.core.Injector
import dvachmovie.fragment.back.BackFragmentDirections
import dvachmovie.fragment.movie.MovieFragmentDirections
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieActivityViewModel
    private lateinit var binding: ActivityMovieBinding

    @Inject
    lateinit var movieTempRepository: MovieTempRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDI()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        viewModel = ViewModelProviders.of(this).get(MovieActivityViewModel::class.java)

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

    private fun initDI() {
        Injector.navigationComponent().inject(this)
    }
}
