package dvachmovie.architecture.binding

import android.net.Uri

object BindingCache {
    var media = listOf<Uri>()
    var cookie = String()
    var pos = Pair<Int, Long>(0, 0)
}
