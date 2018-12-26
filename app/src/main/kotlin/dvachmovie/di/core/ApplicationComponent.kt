package dvachmovie.di.core

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ExoModule::class,
    ViewModelFactoryModule::class,
    RootDataModule::class
])
interface ApplicationComponent {
    fun viewComponent(): FragmentComponent
    fun navigationComponent(): ActivityComponent
    fun workComponent(): WorkerComponent
    fun inject(mainApplication: MainApplication)
}