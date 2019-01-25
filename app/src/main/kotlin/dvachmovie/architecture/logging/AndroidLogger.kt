package dvachmovie.architecture.logging

import android.util.Log
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AndroidLogger @Inject constructor() : Logger {

    init {
        Timber.plant(Timber.DebugTree())
    }

    override fun d(tag: String, message: String) = log(Log.DEBUG, tag, message)

    override fun d(message: String) = log(Log.DEBUG, message)

    override fun e(tag: String, message: String) = log(Log.ERROR, tag, message)

    override fun e(message: String) = log(Log.ERROR, message)

    private fun log(priority: Int, tag: String, message: String) {
        Timber.tag(tag).log(priority, message)
    }

    private fun log(priority: Int, message: String) {
        Timber.log(priority, message)
    }
}