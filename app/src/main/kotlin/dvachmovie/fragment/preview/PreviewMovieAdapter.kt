package dvachmovie.fragment.preview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvachmovie.android.R
import dvachmovie.api.Cookie
import dvachmovie.architecture.Navigator
import dvachmovie.architecture.logging.Logger
import com.dvachmovie.android.databinding.ItemPreviewMoviesBinding
import dvachmovie.db.data.Movie
import dvachmovie.pipe.moviestorage.SetCurrentMoviePipe
import dvachmovie.pipe.network.GetCookiePipe
import javax.inject.Inject

class PreviewMovieAdapter @Inject constructor(
        private val setCurrentMovieStoragePipe: SetCurrentMoviePipe,
        getCookiePipe: GetCookiePipe,
        private val logger: Logger) :
        ListAdapter<Movie, PreviewMovieAdapter.ViewHolder>(PreviewMovieDiffCallback()) {
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
                bind(movie, cookie, createOnClickListener(movie))
            }
        }
    }

    private val cookie = getCookiePipe.execute(Unit)

    private fun createOnClickListener(movie: Movie): View.OnClickListener {
        return View.OnClickListener {
            setCurrentMovieStoragePipe.execute(movie)
            Navigator(it.findNavController(), logger).navigatePreviewToMovieFragment()
        }
    }

    class ViewHolder(
            private val binding: ItemPreviewMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, cookie: Cookie, listener: View.OnClickListener) {
            with(binding) {
                viewModel = PreviewItemVM(movie, cookie)
                clickListener = listener
                executePendingBindings()
            }
        }
    }
}
