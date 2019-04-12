package dvachmovie.fragment.movie

internal object PlayerCache {
    var isPrepared = false
    var playbackPosition: Long = 0
    var shouldAutoPlay: Boolean = true
    var countPlayed = 0
}
