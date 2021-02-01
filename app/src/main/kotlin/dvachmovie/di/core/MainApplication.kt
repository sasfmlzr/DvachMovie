package dvachmovie.di.core

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.prepare(this)
        Injector.applicationComponent().inject(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}
