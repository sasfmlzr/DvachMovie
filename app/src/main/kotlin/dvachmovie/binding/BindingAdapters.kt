package dvachmovie.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
                .load(imageUrl)
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