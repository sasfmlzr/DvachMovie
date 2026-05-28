package dvachmovie.architecture.binding

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util

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
