package dvachmovie.fragment.preview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dvachmovie.R
import dvachmovie.api.Cookie
import dvachmovie.api.CookieManager
import dvachmovie.architecture.Navigator
import dvachmovie.architecture.ViewModelFactory
import dvachmovie.databinding.ItemPreviewMoviesBinding
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieStorage
import javax.inject.Inject

class PreviewMovieAdapter @Inject constructor(private val movieStorage: MovieStorage,
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
                bind(movie, cookieManager.getCookie(), createOnClickListener(movie))
            }
        }
    }

    private fun createOnClickListener(movie: MovieEntity): View.OnClickListener {
        return View.OnClickListener {
            movieStorage.currentMovie.value = movie
            Navigator(it.findNavController()).navigatePreviewToMovieFragment()
        }
    }

    class ViewHolder(
            private val binding: ItemPreviewMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity, cookie: Cookie, listener: View.OnClickListener) {
            with(binding) {
                viewModel = PreviewItemVM(movie, cookie)
                clickListener = listener
                executePendingBindings()
            }
        }
    }
}
