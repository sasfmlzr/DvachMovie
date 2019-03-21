package dvachmovie.fragment.preview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dvachmovie.R
import dvachmovie.api.CookieManager
import dvachmovie.architecture.Navigator
import dvachmovie.databinding.ItemPreviewMoviesBinding
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieRepository
import javax.inject.Inject

class PreviewMovieAdapter @Inject constructor(private val movieRepository: MovieRepository,
                                              private val cookieManager: CookieManager) :
        ListAdapter<MovieEntity, PreviewMovieAdapter.ViewHolder>
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
                bind(movie, cookieManager.cookie, createOnClickListener(movie))
            }

        }
    }

    private fun createOnClickListener(movie: MovieEntity): View.OnClickListener {
        return View.OnClickListener {
            movieRepository.getCurrent().value = movie
            Navigator(it.findNavController()).navigatePreviewToMovieFragment()
        }
    }

    class ViewHolder(
            private val binding: ItemPreviewMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity, cookie: CookieManager.Cookie, listener: View.OnClickListener) {
            with(binding) {
                viewModel = PreviewItemVM(movie, cookie)
                clickListener = listener
                executePendingBindings()
            }
        }
    }
}
