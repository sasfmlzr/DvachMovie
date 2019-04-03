package dvachmovie.di.core

import dagger.Module
import dagger.Provides
import dvachmovie.R
import javax.inject.Named

@Module
class DemoViewModule {

    @Provides
    @Named("settingsLayout")
    fun getSettingsLayout() = R.layout.fragment_settings.toString()
}
