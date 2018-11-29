package dvachmovie.fragment.preview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dvachmovie.R
import dvachmovie.databinding.ItemPreviewMoviesBinding
import dvachmovie.repository.local.Movie
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class PreviewMovieAdapter @Inject constructor(private val movieTempRepository: MovieTempRepository) :
        ListAdapter<String, PreviewMovieAdapter.ViewHolder>
        (PreviewMovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_preview_movies, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { movie ->
            with(holder) {
                itemView.tag = movie
                bind(movie, createOnClickListener(movie))
            }

        }
    }

    private fun createOnClickListener(tempUri: String): View.OnClickListener {
        return View.OnClickListener {
            var movie = Movie()
            movieTempRepository.movieLists.map { currentMovie ->
                if (currentMovie.moviePreviewUrl == tempUri) {
                    movie = currentMovie
                }
            }
            val direction = PreviewFragmentDirections.ActionShowMovieFragment(movie)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
            private val binding: ItemPreviewMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: String, listener: View.OnClickListener) {
            with(binding) {
                viewModel = PreviewItemVM(movie)
                clickListener = listener
                executePendingBindings()
            }
        }
    }
}