package dvachmovie.architecture.binding

import android.content.Context
import android.net.Uri
import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import dvachmovie.architecture.binding.BindingCache.pos
import dvachmovie.fragment.movie.PlayerCache

@BindingAdapter("cookie")
fun PlayerView.bindCookie(cookies: String) {
    BindingCache.cookie = cookies
    if (BindingCache.media.isNotEmpty()) {
        bindPlayer(this)
    }
}

@BindingAdapter("movie")
fun PlayerView.bindMovie(urlVideo: List<Uri>?) {
    if (urlVideo == null) return
    if (urlVideo.isNotEmpty()) {
        BindingCache.media = urlVideo
        (this.player as SimpleExoPlayer)
        if (!PlayerCache.isPrepared) {
            bindPlayer(this)
        }
    }
}

@BindingAdapter("movie_position")
fun PlayerView.bindCurrentPosition(value: Pair<Int, Long>) {
    pos = value
    val default = 0.toLong()
    if (pos.second == default) {
        this.player?.seekToDefaultPosition(pos.first)
    } else {
        this.player?.seekTo(pos.first, pos.second)
    }
}

fun bindPlayer(playerView: PlayerView) {
    if (BindingCache.media.isNotEmpty()) {
        (playerView.player as SimpleExoPlayer).setMediaSource(
            createMediaSourcesByUri(
                BindingCache.media,
                playerView.context
            ), true
        )
        (playerView.player as SimpleExoPlayer)
            .prepare()
    }
}

private fun createMediaSourcesByUri(
    urlVideo: List<Uri>?,
    context: Context
): ConcatenatingMediaSource {
    val agent = Util.getUserAgent(context, "AppName")
    val defaultHttpDataSource = DefaultHttpDataSource.Factory()
    defaultHttpDataSource.setUserAgent(agent)

    defaultHttpDataSource.setDefaultRequestProperties(mapOf(Pair("Cookie", BindingCache.cookie)))

    val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
        context,
        null, defaultHttpDataSource
    )

    val mediaSources = ConcatenatingMediaSource()
    urlVideo?.map { url ->
        val mediaItem = MediaItem.Builder().setUri(url).build()
        ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)
    }?.let { mediaSources.addMediaSources(it) }
    return mediaSources
}
