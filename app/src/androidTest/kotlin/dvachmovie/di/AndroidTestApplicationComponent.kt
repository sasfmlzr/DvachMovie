package dvachmovie.di

import dagger.Component
import dvachmovie.di.core.ApplicationModule
import dvachmovie.di.core.ExoModule
import dvachmovie.di.core.FullApplicationComponent
import dvachmovie.di.core.RootDataModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ExoModule::class,
    MockRootDataAndroidModule::class,
    RootDataModule::class
])
interface AndroidTestApplicationComponent : FullApplicationComponent {
    fun testComponent(): AndroidTestComponent
}