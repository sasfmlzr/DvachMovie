package dvachmovie.api

import dvachmovie.Constraints.Companion.BASE_URL
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitSingleton {
    private var dvachMovieApi: DvachMovieApi? = null

    fun getDvachMovieApi(): DvachMovieApi? {
        if (dvachMovieApi == null) {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            dvachMovieApi = retrofit.create(DvachMovieApi::class.java)
        }
        return dvachMovieApi
    }
}