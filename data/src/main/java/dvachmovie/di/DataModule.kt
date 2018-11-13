package dvachmovie.di

import dagger.Module
import dagger.Provides
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Singleton

@Module(includes = [RoomModule::class, ApiModule::class])
class DataModule {
    @Singleton
    @Provides
    internal fun movieTempListRepository() = MovieTempRepository()
}