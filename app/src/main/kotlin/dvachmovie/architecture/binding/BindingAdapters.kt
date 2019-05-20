package dvachmovie.architecture.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dvachmovie.api.Cookie
import dvachmovie.db.data.Movie
import dvachmovie.db.data.MovieEntity

@BindingAdapter("imageFromUrl", "cookie", requireAll = true)
fun ImageView.bindImageFromUrl(movieEntity: Movie, cookie: Cookie) {
    if (movieEntity.previewUrl.isNotEmpty()) {
        val builder = LazyHeaders.Builder().addHeader("Cookie", cookie.toString())

        val glideUrl = GlideUrl(movieEntity.previewUrl, builder.build())

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

@BindingAdapter("showBanner")
fun AdView.showBanner(sdkKey: String) {
    MobileAds.initialize(this.context, sdkKey)

    val request = AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .addTestDevice("8F3FC8DD5008F6A17A373A2B3DC259FB")
            .build()
    this.loadAd(request)
}
