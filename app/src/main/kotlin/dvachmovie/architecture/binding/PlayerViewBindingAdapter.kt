package dvachmovie.architecture.binding

import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dvachmovie.db.data.MovieEntity

@BindingMethods(
        BindingMethod(
                type = PlayerView::class,
                attribute = "movie",
                method = "bindCurrentMovie"
        )
)
class PlayerViewBindingAdapter {
    companion object {
        private var media = ConcatenatingMediaSource()

        @BindingAdapter("movie")
        @JvmStatic
        fun bindMovie(playerView: PlayerView, values: List<MovieEntity>) {
            if (values.isNotEmpty()) {
                val urlVideo: List<Uri> = values.map { value -> Uri.parse(value.movieUrl) }

                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(playerView.context,
                        Util.getUserAgent(playerView.context, "AppName"))

                val videoSources = urlVideo.map { url ->
                    ExtractorMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(url) as MediaSource
                } as MutableList<MediaSource>

                val mediaSources = ConcatenatingMediaSource()

                videoSources.map { url -> mediaSources.addMediaSource(url) }
                media = mediaSources
                bindPlayer(playerView)
            }
        }

        fun bindPlayer(playerView: PlayerView){
            (playerView.player as SimpleExoPlayer).prepare(media, true, false)
        }

        @BindingAdapter("movie_position")
        @JvmStatic
        fun bindCurrentPosition(playerView: PlayerView, value: Pair<Int, Long>) {
            val default = 0.toLong()
            if (value.second == default) {
                playerView.player.seekToDefaultPosition(value.first)
            } else {
                playerView.player.seekTo(value.first, value.second)
            }
        }

    }
}