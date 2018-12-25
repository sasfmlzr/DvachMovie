package dvachmovie.di.core

import dagger.Module

@Module(includes = [
    ApiModule::class,
    DataModule::class,
    StorageModule::class,
    RoomModule::class
])
class RootDataModule