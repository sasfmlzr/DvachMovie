package dvachmovie.fragment.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dvachmovie.base.BaseFragment
import dvachmovie.databinding.FragmentPreviewMoviesBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class PreviewFragment : BaseFragment<PreviewVM,
        FragmentPreviewMoviesBinding>(PreviewVM::class.java) {

    @Inject
    lateinit var movieTempRepository: MovieTempRepository
    @Inject
    lateinit var adapter: PreviewMovieAdapter

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentPreviewMoviesBinding.inflate(inflater, container, false)

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
        if (movieTempRepository.movieList.value
                !!.contains(movieTempRepository.currentMovie.value)) {
            pos = movieTempRepository.movieList.value!!.indexOf(movieTempRepository.currentMovie.value)
        }

        if (pos < 70) {
            binding.moviesList.smoothScrollToPosition(pos)
        } else {
            binding.moviesList.scrollToPosition(pos)
        }
    }
}