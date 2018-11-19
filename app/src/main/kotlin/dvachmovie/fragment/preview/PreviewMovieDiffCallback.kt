package dvachmovie.fragment.preview

import android.support.v7.util.DiffUtil
import dvachmovie.repository.local.Movie

class PreviewMovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(p0: Movie, p1: Movie): Boolean {
        return p0.moviePreviewUrl == p1.moviePreviewUrl
    }

    override fun areContentsTheSame(p0: Movie, p1: Movie): Boolean {
        return p0.moviePreviewUrl == p1.moviePreviewUrl
    }


}