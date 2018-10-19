package dvachmovie.di.core

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.prepare(this)
        Injector.applicationComponent().inject(this)
    }

}