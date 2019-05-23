package dvachmovie.di.core

import dagger.Component
import dvachmovie.di.RootDataAndroidModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ExoModule::class,
    ViewModelFactoryModule::class,
    RootDataAndroidModule::class,
    RootDataModule::class
])
interface DemoApplicationComponent {
    fun viewComponent(): FragmentComponent
    fun navigationComponent(): ActivityComponent
    fun workComponent(): WorkerComponent
    fun serviceComponent(): ServiceComponent
    fun inject(mainApplication: MainApplication)
}
