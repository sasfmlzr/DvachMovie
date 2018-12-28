package dvachmovie.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.ui.PlayerView

@BindingMethods(
        BindingMethod(
                type = PlayerView::class,
                attribute = "movie",
                method = "bindCurrentMovie"
        )
)
class PlayerViewBindingAdapter {
    companion object {
        @BindingAdapter("movie")
        @JvmStatic
        fun bindMovie(playerView: PlayerView, mediaSources: ConcatenatingMediaSource) {
            if (mediaSources.size != 0) {
                (playerView.player as SimpleExoPlayer).prepare(mediaSources, false, false)
            }
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