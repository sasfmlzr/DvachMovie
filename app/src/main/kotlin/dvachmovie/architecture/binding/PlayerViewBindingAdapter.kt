package dvachmovie.architecture.binding

import android.net.Uri
import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

object BindingCache {
    var media = ConcatenatingMediaSource()
    var cookie = String()
}

@BindingAdapter("cookie")
fun PlayerView.bindCookie(cookies: String) {
    BindingCache.cookie = cookies
    bindPlayer(this)

}

@BindingAdapter("movie")
fun PlayerView.bindMovie(urlVideo: List<Uri>) {
    if (urlVideo.isNotEmpty()) {

        val agent = Util.getUserAgent(this.context, "AppName")

        val defaultHttpDataSource = DefaultHttpDataSourceFactory(agent, null)
        defaultHttpDataSource.defaultRequestProperties.set("Cookie", BindingCache.cookie)

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this.context,
                null, defaultHttpDataSource)

        dataSourceFactory.createDataSource().responseHeaders

        val videoSources = urlVideo.map { url ->
            ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(url) as MediaSource
        } as MutableList<MediaSource>

        val mediaSources = ConcatenatingMediaSource()

        videoSources.map { url -> mediaSources.addMediaSource(url) }
        BindingCache.media = mediaSources
        bindPlayer(this)
    }
}

@BindingAdapter("movie_position")
fun PlayerView.bindCurrentPosition(value: Pair<Int, Long>) {
    val default = 0.toLong()
    if (value.second == default) {
        this.player.seekToDefaultPosition(value.first)
    } else {
        this.player.seekTo(value.first, value.second)
    }
}

fun bindPlayer(playerView: PlayerView) {
    (playerView.player as SimpleExoPlayer).prepare(BindingCache.media, true, false)
}
