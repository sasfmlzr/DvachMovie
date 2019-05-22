package dvachmovie.di.core

import dagger.Module

@Module(includes = [
    ApiModule::class,
    StorageModule::class,
    RepositoryModule::class
])
class RootDataModule
