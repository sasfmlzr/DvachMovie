package dvachmovie.di

import dagger.Module

@Module(includes = arrayOf(RoomModule::class, ApiModule::class))
class DataModule {
}