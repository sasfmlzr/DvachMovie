package dvachmovie.fragment.preview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import dvachmovie.db.data.Movie

class PreviewMovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(p0: Movie, p1: Movie): Boolean {
        return p0 == p1
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(p0: Movie, p1: Movie): Boolean {
        return p0 == p1
    }
}
