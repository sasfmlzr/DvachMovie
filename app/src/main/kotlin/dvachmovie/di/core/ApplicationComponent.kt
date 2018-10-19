package dvachmovie.di.core

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    //fun viewModelComponent(): ViewModelComponent
    fun viewComponent(): ViewComponent
    fun navigationComponent(): NavigationComponent
    fun inject(mainApplication: MainApplication)
}