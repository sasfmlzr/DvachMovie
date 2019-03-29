package dvachmovie.architecture.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import dvachmovie.api.Cookie
import dvachmovie.db.data.MovieEntity

@BindingAdapter("imageFromUrl", "cookie")
fun ImageView.bindImageFromUrl(movieEntity: MovieEntity, cookie: Cookie) {
    if (!movieEntity.previewUrl.isEmpty()) {
        val builder = LazyHeaders.Builder().addHeader("Cookie", cookie.toString())

        val glideUrl = GlideUrl(movieEntity.previewUrl, builder.build())

        Glide.with(this.context)
                .load(glideUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
    }
}

@BindingAdapter("imageFromResource")
fun bindImageFromGif(view: ImageView, resourceId: Int) {
        Glide.with(view.context)
                .load(resourceId)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
}
