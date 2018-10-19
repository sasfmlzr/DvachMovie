package dvachmovie.di.core

object Injector {

    private lateinit var component: ApplicationComponent

    fun prepare(application: MainApplication) {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(application))
                .build()
    }

    fun applicationComponent() = component
    fun navigationComponent() = component.navigationComponent()
    fun viewComponent() = component.viewComponent()

}