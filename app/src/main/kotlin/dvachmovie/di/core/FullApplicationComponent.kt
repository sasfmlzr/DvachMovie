package dvachmovie.di.core

import dagger.Component
import dvachmovie.di.RootDataAndroidModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ExoModule::class,
    ViewModelFactoryModule::class,
    RootDataModule::class,
    RootDataAndroidModule::class
])
interface FullApplicationComponent : DemoApplicationComponent
