package dvachmovie.di.core

object Injector {

    private lateinit var component: ApplicationComponent

    fun prepare(application: MainApplication) {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(application))
                .roomModule(RoomModule(application))
                .build()
    }

    fun applicationComponent() = component
    fun navigationComponent() = component.navigationComponent()
    fun workComponent() = component.workComponent()
    fun viewComponent() = component.viewComponent()
    fun serviceComponent() = component.serviceComponent()
}
