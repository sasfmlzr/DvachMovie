package dvachmovie.di.core

import dvachmovie.di.RoomModule

object Injector {

    lateinit var component: FullApplicationComponent

    fun prepare(application: MainApplication) {
        component = DaggerFullApplicationComponent.builder()
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
