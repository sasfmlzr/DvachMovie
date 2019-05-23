package dvachmovie.api

data class Cookie(val header: String,
                  val value: String) {

    override fun toString(): String = "$header=$value"
}
