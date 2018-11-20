package dvachmovie.activity.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import dvachmovie.R
import dvachmovie.databinding.ActivityMovieBinding
import dvachmovie.di.core.Injector
import dvachmovie.fragment.movie.MovieFragment

class MovieActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieActivityViewModel
    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDI()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        viewModel = ViewModelProviders.of(this).get(MovieActivityViewModel::class.java)

        binding.viewmodel = viewModel
    }

    private fun initDI() {
        Injector.navigationComponent().inject(this)
    }

    override fun onSupportNavigateUp() =
            findNavController(this, R.id.navHostFragment).navigateUp()
}
