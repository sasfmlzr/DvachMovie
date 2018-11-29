package dvachmovie.fragment.preview

import androidx.recyclerview.widget.DiffUtil
import dvachmovie.repository.local.Movie

class PreviewMovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(p0: Movie, p1: Movie): Boolean {
        return p0 == p1
    }

    override fun areContentsTheSame(p0: Movie, p1: Movie): Boolean {
        return p0 == p1
    }
}