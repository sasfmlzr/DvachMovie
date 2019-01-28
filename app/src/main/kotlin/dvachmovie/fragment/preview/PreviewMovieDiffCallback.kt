package dvachmovie.fragment.preview

import androidx.recyclerview.widget.DiffUtil
import dvachmovie.db.data.MovieEntity

class PreviewMovieDiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(p0: MovieEntity, p1: MovieEntity): Boolean {
        return p0 == p1
    }

    override fun areContentsTheSame(p0: MovieEntity, p1: MovieEntity): Boolean {
        return p0 == p1
    }
}
