package dvachmovie.fragment.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.exoplayer2.ui.PlayerView
import dvachmovie.base.BaseFragment
import dvachmovie.databinding.FragmentMovieBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.listener.OnSwipeTouchListener
import dvachmovie.repository.local.Movie
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieFragment : BaseFragment<MovieVM,
        FragmentMovieBinding>(MovieVM::class.java) {

    @Inject
    lateinit var movieTempRepository: MovieTempRepository

    private lateinit var player: PlayerView

    override fun inject(component: FragmentComponent) = component.inject(this)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentMovieBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(viewLifecycleOwner)

        player = binding.playerView
        player.setOnTouchListener(onGestureListener())

        viewModel.getPosition().value = movieTempRepository.getIndexPosition()

        binding.shuffleButton.setOnClickListener {
            movieTempRepository.movieList
                    .value = movieTempRepository.movieList.value!!.shuffled() as MutableList<Movie>
        }

        return binding.root
    }

    override fun onStop() {
        movieTempRepository.currentMovie.value = movieTempRepository.movieList.value!![player.player.currentWindowIndex]
        player.player.stop()
        super.onStop()
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
                val movieUri = binding.viewModel!!.getUrlList().value?.get(player.player.currentPeriodIndex)
                movieTempRepository.currentMovie.value = movieUri
                val direction = MovieFragmentDirections
                        .ActionShowPreviewFragment()
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
            binding.shuffleButton.visibility = INVISIBLE
            player.hideController()
        } else {
            binding.shuffleButton.visibility = VISIBLE
            player.showController()
        }
    }

    private fun findMovieInRepository(movieUri: String): Movie {
        var movie = Movie()
        movieTempRepository.movieList.value?.map { it ->
            if (it.movieUrl == movieUri) {
                movie = it
            }
        }
        return movie
    }
}