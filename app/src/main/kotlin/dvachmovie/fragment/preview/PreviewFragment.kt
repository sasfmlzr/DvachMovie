package dvachmovie.fragment.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dvachmovie.base.BaseFragment
import dvachmovie.databinding.FragmentPreviewMoviesBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class PreviewFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var movieTempRepository: MovieTempRepository
    @Inject
    lateinit var adapter: PreviewMovieAdapter

    private lateinit var binding: FragmentPreviewMoviesBinding

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        if (arguments?.size() != 0) {
            val movie = PreviewFragmentArgs.fromBundle(arguments).currentMovie
            movieTempRepository.currentMovie = movie
        }

        binding = FragmentPreviewMoviesBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(PreviewViewModel::class.java)
        binding.viewModel = viewModel
        binding.moviesList.adapter = adapter

        subscribeUi(adapter)
        configureScrollRecyclerView()

        return binding.root
    }

    private fun subscribeUi(adapter: PreviewMovieAdapter) {
        binding.viewModel!!.getUriMovie()
                .observe(viewLifecycleOwner, Observer { movie ->
                    if (movie != null) adapter.submitList(movie)
                })
    }

    private fun configureScrollRecyclerView() {
        var pos = 0
        if (movieTempRepository.movieLists
                        .contains(movieTempRepository.currentMovie)) {
            pos = movieTempRepository.movieLists.indexOf(movieTempRepository.currentMovie)
        }

        if (pos < 70) {
            binding.moviesList.smoothScrollToPosition(pos)
        } else {
            binding.moviesList.scrollToPosition(pos)
        }
    }
}