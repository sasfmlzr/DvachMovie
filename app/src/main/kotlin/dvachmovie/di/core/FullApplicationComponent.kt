package dvachmovie.di.core

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ExoModule::class,
    ViewModelFactoryModule::class,
    RootDataModule::class,
    FullViewModule::class
])
interface FullApplicationComponent : DemoApplicationComponent
