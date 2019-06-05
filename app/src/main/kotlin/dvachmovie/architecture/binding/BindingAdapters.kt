package dvachmovie.architecture.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dvachmovie.api.Cookie
import dvachmovie.db.data.Movie

@BindingAdapter("imageFromUrl", "cookie", requireAll = true)
fun ImageView.bindImageFromUrl(movie: Movie, cookie: Cookie) {
    if (movie.previewUrl.isNotEmpty()) {
        val builder = LazyHeaders.Builder().addHeader("Cookie", cookie.toString())

        val glideUrl = GlideUrl(movie.previewUrl, builder.build())

        Glide.with(this)
                .load(glideUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
    }
}

@BindingAdapter("imageFromResource")
fun ImageView.bindImageFromResource(resourceId: Int) {
    Glide.with(this)
            .load(resourceId)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}
