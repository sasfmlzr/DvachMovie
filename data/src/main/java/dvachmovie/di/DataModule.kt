package dvachmovie.di

import dagger.Module
import dagger.Provides
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Singleton

@Module(includes = arrayOf(RoomModule::class, ApiModule::class))
class DataModule {
    @Singleton
    @Provides
    fun movieTempListRepository() = MovieTempRepository()
}