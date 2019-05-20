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
import dvachmovie.architecture.binding.BindingCache.pos

@BindingAdapter("cookie")
fun PlayerView.bindCookie(cookies: String) {
    BindingCache.cookie = cookies
    bindPlayer(this)
}

@BindingAdapter("movie")
fun PlayerView.bindMovie(urlVideo: List<Uri>?) {
    if (urlVideo != null) {
        if (urlVideo.isNotEmpty()) {
            val agent = Util.getUserAgent(this.context, "AppName")
            val defaultHttpDataSource = DefaultHttpDataSourceFactory(agent, null)
            defaultHttpDataSource.defaultRequestProperties.set("Cookie", BindingCache.cookie)

            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this.context,
                    null, defaultHttpDataSource)

            val mediaSources = ConcatenatingMediaSource()
            mediaSources.addMediaSources(urlVideo.map { url ->
                ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(url) as MediaSource
            })

            BindingCache.media = mediaSources
            bindPlayer(this)
        }
    }

}

@BindingAdapter("movie_position")
fun PlayerView.bindCurrentPosition(value: Pair<Int, Long>) {
    pos = value
    val default = 0.toLong()
    if (pos.second == default) {
        this.player.seekToDefaultPosition(pos.first)
    } else {
        this.player.seekTo(pos.first, pos.second)
    }
}

fun bindPlayer(playerView: PlayerView) {
    (playerView.player as SimpleExoPlayer)
            .prepare(BindingCache.media,
                    true,
                    false)
}
