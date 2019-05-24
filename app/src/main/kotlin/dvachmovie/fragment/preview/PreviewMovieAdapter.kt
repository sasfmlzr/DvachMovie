package dvachmovie.fragment.preview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dvachmovie.R
import dvachmovie.api.Cookie
import dvachmovie.architecture.Navigator
import dvachmovie.architecture.ScopeProvider
import dvachmovie.architecture.logging.Logger
import dvachmovie.databinding.ItemPreviewMoviesBinding
import dvachmovie.db.data.Movie
import dvachmovie.usecase.SetCurrentMovieStorageUseCase
import dvachmovie.usecase.real.GetCookieUseCase
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PreviewMovieAdapter @Inject constructor(
        private val setCurrentMovieStorageUseCase: SetCurrentMovieStorageUseCase,
        private val getCookieUseCase: GetCookieUseCase,
        private val scopeProvider: ScopeProvider,
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
                runBlocking {
                    bind(movie, getCookieUseCase.execute(Unit), createOnClickListener(movie))
                }
            }
        }
    }

    private fun createOnClickListener(movie: Movie): View.OnClickListener {
        return View.OnClickListener {
            runBlocking {
                setCurrentMovieStorageUseCase.execute(movie)
                Navigator(it.findNavController(), logger).navigatePreviewToMovieFragment()
            }
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
