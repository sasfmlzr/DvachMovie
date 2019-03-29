package dvachmovie.architecture.binding

import com.google.android.exoplayer2.source.ConcatenatingMediaSource

object BindingCache {
    var media = ConcatenatingMediaSource()
    var cookie = String()
    var pos = Pair<Int, Long>(0, 0)
}
