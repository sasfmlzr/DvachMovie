package dvachmovie.di.core

import dagger.Component
import dvachmovie.di.DataModule
import dvachmovie.di.WorkerComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    DataModule::class,
    ExoModule::class,
    ViewModelFactoryModule::class
])
interface ApplicationComponent {
    fun viewComponent(): FragmentComponent
    fun navigationComponent(): ActivityComponent
    fun workerComponent(): WorkerComponent
    fun inject(mainApplication: MainApplication)
}