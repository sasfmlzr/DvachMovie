package dvachmovie.fragment.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.exoplayer2.ui.PlayerView
import dvachmovie.R
import dvachmovie.base.BaseFragment
import dvachmovie.databinding.FragmentMovieBinding
import dvachmovie.di.core.ViewComponent
import dvachmovie.listener.OnSwipeTouchListener
import dvachmovie.repository.local.Movie
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var movieTempRepository: MovieTempRepository

    private lateinit var binding: FragmentMovieBinding

    private lateinit var player: PlayerView

    override fun inject(component: ViewComponent) = component.inject(this)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentMovieBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MovieViewModel::class.java)
        binding.viewmodel = viewModel

        player = binding.playerView

        player.setOnTouchListener(onGestureListener())

        return binding.root
    }

    private fun onGestureListener(): OnSwipeTouchListener {
        return object : OnSwipeTouchListener(context!!) {
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View, event: MotionEvent): Boolean {

                if (event.action == MotionEvent.ACTION_DOWN) {
                    toggleControlsVisibility()
                }
                return super.onTouch(v, event)
            }

            override fun onSwipeRight() {
                println("onSwipeRight")
            }

            override fun onSwipeLeft() {
                println("onSwipeLeft")
            }

            override fun onSwipeTop() {
                val movieUri = binding.viewmodel?.uriMovie?.value?.get(player.player.currentPeriodIndex)
                var movie = Movie()
                movieTempRepository.movieLists.map { it ->
                    if (it.movieUrl == movieUri){
                        movie = it
                    }
                }
                val direction = MovieFragmentDirections.ActionShowPreviewFragment(movie)
                findNavController(this@MovieFragment).navigate(direction)
                println("onSwipeTop")
            }

            override fun onSwipeBottom() {
                println("onSwipeBottom")
            }
        }
    }

    private fun toggleControlsVisibility() {
        if (player.isControllerVisible) {
            player.hideController()
        } else {
            player.showController()
        }
    }
}
