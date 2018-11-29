package dvachmovie.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dvachmovie.repository.local.Movie

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, movie: Movie?) {
    if (!movie!!.moviePreviewUrl.isEmpty()) {
        Glide.with(view.context)
                .load(movie.moviePreviewUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}

@BindingAdapter("imageFromDrawable")
fun bindImageFromDrawable(view: ImageView, image: Drawable?) {
    if (image != null) {
        Glide.with(view.context)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}