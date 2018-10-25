package di

import dagger.Module

@Module(includes = arrayOf(RoomModule::class))
class DataModule {
}