package dvachmovie.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun retrofit(
    ) = Retrofit.Builder()
            .baseUrl("https://2ch.hk")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}