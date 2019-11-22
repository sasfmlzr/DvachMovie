package dvachmovie.di.architecture.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dvachmovie.di.RoomModule
import javax.inject.Singleton

@Module(includes = [RoomModule::class,
    MockStorageModule::class])
class MockRootDataAndroidModule {

    @Provides
    @Singleton
    internal fun sharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
}
