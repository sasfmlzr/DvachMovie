package dvachmovie.storage.base

import android.annotation.SuppressLint
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("ApplySharedPref")
@Singleton
internal class SharedPreferencesStorage @Inject constructor(
        private val prefs: SharedPreferences
) : KeyValueStorage {

    @Synchronized
    override fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).commit()
    }

    override fun getString(key: String): String? =
            prefs.getString(key, null)

    @Synchronized
    override fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).commit()
    }

    override fun getInt(key: String): Int? =
            if (prefs.contains(key)) {
                prefs.getInt(key, 0)
            } else {
                null
            }

    @Synchronized
    override fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).commit()
    }

    override fun getBoolean(key: String): Boolean? =
            if (prefs.contains(key)) {
                prefs.getBoolean(key, false)
            } else {
                null
            }

    @Synchronized
    override fun remove(vararg key: String) {
        if (key.isEmpty()) {
            return
        }
        val editor = prefs.edit()
        key.forEach { editor.remove(it) }
        editor.commit()
    }

    override fun contains(key: String): Boolean =
            prefs.contains(key)

    override fun getAll(): Map<String, *> =
            prefs.all
}
