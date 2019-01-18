package dvachmovie.storage.base

interface KeyValueStorage {
    fun putString(key: String, value: String)
    fun getString(key: String): String?
    fun putInt(key: String, value: Int)
    fun getInt(key: String): Int?
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean?
    fun remove(vararg key: String)
    fun contains(key: String): Boolean
    fun getAll(): Map<String, *>
}