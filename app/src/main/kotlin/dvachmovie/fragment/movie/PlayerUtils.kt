package dvachmovie.fragment.movie

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Timeline

private const val MAX_POSITION_FOR_SEEK_TO_PREVIOUS: Long = 3000

fun previous(player: Player) {
    val timeline = player.currentTimeline
    if (timeline.isEmpty) {
        return
    }
    val windowIndex = player.currentWindowIndex
    val window = Timeline.Window()
    timeline.getWindow(windowIndex, window)
    val previousWindowIndex = player.previousWindowIndex

    if (previousWindowIndex != C.INDEX_UNSET &&
            (player.currentPosition <= MAX_POSITION_FOR_SEEK_TO_PREVIOUS ||
                    window.isDynamic && !window.isSeekable)) {
        player.seekTo(previousWindowIndex, C.TIME_UNSET)
    } else {
        player.seekTo(0)
    }
}

fun next(player: Player) {
    val timeline = player.currentTimeline
    if (timeline.isEmpty) {
        return
    }
    val windowIndex = player.currentWindowIndex
    val window = Timeline.Window()
    val nextWindowIndex = player.nextWindowIndex
    if (nextWindowIndex != C.INDEX_UNSET) {
        player.seekTo(nextWindowIndex, C.TIME_UNSET)
    } else if (timeline.getWindow(windowIndex, window, false).isDynamic) {
        player.seekTo(windowIndex, C.TIME_UNSET)
    }
}
