package dvachmovie.di.core

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dvachmovie.AppConfig
import dvachmovie.BuildConfig
import dvachmovie.PresenterModel
import dvachmovie.architecture.logging.AndroidLogger
import dvachmovie.architecture.logging.Logger
import dvachmovie.di.base.FragmentScope
import dvachmovie.usecase.base.UseCaseModel
import kotlinx.coroutines.channels.BroadcastChannel
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MainApplication) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application = application

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    internal fun logger(androidLogger: AndroidLogger): Logger = androidLogger

    @Provides
    @Singleton
    internal fun appConfig(): AppConfig = AppConfig(BuildConfig.DVACH_URL)
}
