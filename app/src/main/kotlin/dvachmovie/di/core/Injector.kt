package dvachmovie.di.core

import dvachmovie.BuildConfig

object Injector {

    private lateinit var component: DemoApplicationComponent

    fun prepare(application: MainApplication) {
        when (BuildConfig.FULL_VERSION) {
            true -> component = DaggerFullApplicationComponent.builder()
                    .applicationModule(ApplicationModule(application))
                    .roomModule(RoomModule(application))
                    .build()
            false -> component = DaggerDemoApplicationComponent.builder()
                    .applicationModule(ApplicationModule(application))
                    .roomModule(RoomModule(application))
                    .build()
        }
    }

    fun applicationComponent() = component
    fun navigationComponent() = component.navigationComponent()
    fun workComponent() = component.workComponent()
    fun viewComponent() = component.viewComponent()
    fun serviceComponent() = component.serviceComponent()
}
