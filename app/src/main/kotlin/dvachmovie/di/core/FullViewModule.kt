package dvachmovie.di.core

import dagger.Module
import dagger.Provides
import dvachmovie.R
import javax.inject.Named

@Module
class FullViewModule {

    @Provides
    @Named("settingsLayout")
    fun getSettingsLayout() = R.layout.fragment_settings_full.toString()
}
