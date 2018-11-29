package dvachmovie.di.core

import dagger.Component
import dvachmovie.di.DataModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class,
    ExoModule::class, ExoModule::class, ViewModuleFactoryModule::class])
interface ApplicationComponent {

    fun viewComponent(): ViewComponent
    fun navigationComponent(): NavigationComponent
    fun inject(mainApplication: MainApplication)
}