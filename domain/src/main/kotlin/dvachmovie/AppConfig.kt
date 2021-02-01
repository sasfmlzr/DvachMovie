package dvachmovie

object AppConfig {
    var currentBaseUrl: String = ""
    const val DVACH_URL: String = "https://2ch.hk"
    const val FOURCHAN_URL: String = "http://a.4cdn.org"
    const val FOURCHAN_WEBM_URL: String = "https://i.4cdn.org"
    const val FOURCHAN_THUMBNAIL_URL: String = "http://i.4cdn.org"
    const val NEOCHAN_URL: String = "https://neochan.net"

    val imageboards = hashMapOf(DVACH_URL to "2ch.hk",
            FOURCHAN_URL to "4chan.org",
            NEOCHAN_URL to "neochan.net")
}
