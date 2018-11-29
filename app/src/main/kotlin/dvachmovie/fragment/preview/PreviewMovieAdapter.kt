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
        ListAdapter<Movie, PreviewMovieAdapter.ViewHolder>
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

    private fun createOnClickListener(movie: Movie): View.OnClickListener {
        return View.OnClickListener {
            movieTempRepository.currentMovie.value = movie
            val direction = PreviewFragmentDirections.ActionShowMovieFragment()
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
            private val binding: ItemPreviewMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, listener: View.OnClickListener) {
            with(binding) {
                viewModel = PreviewItemVM(movie)
                clickListener = listener
                executePendingBindings()
            }
        }
    }
}