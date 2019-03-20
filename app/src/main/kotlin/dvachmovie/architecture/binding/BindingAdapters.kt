package dvachmovie.architecture.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dvachmovie.db.data.MovieEntity

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, movieEntity: MovieEntity?) {
    if (!movieEntity!!.previewUrl.isEmpty()) {
        Glide.with(view.context)
                .load(movieEntity.previewUrl)
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
