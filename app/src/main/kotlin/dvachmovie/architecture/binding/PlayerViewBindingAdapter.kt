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


class PlayerViewBindingAdapter {
    companion object {
        private var media = ConcatenatingMediaSource()

        @JvmStatic
        @BindingAdapter("movie", "cookie")
        fun PlayerView.bindMovie(urlVideo: List<Uri>, cookie: String) {
            if (urlVideo.isNotEmpty()) {

                val agent = Util.getUserAgent(this.context, "AppName")

                val defaultHttpDataSource = DefaultHttpDataSourceFactory(agent, null)
                defaultHttpDataSource.defaultRequestProperties.set("Cookie", cookie)

                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this.context,
                        null, defaultHttpDataSource)

                dataSourceFactory.createDataSource().responseHeaders

                val videoSources = urlVideo.map { url ->
                    ExtractorMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(url) as MediaSource
                } as MutableList<MediaSource>

                val mediaSources = ConcatenatingMediaSource()

                videoSources.map { url -> mediaSources.addMediaSource(url) }
                media = mediaSources
                bindPlayer(this)
            }
        }

        @JvmStatic
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
            (playerView.player as SimpleExoPlayer).prepare(media, true, false)
        }
    }
}
