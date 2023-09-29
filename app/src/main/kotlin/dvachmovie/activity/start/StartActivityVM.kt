package dvachmovie.activity.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvachmovie.android.BuildConfig
import dvachmovie.db.data.Movie
import dvachmovie.pipe.db.DeleteOldMoviesPipe
import dvachmovie.pipe.moviestorage.EraseMovieStoragePipe
import dvachmovie.pipe.moviestorage.SetMovieChangedListenerPipe
import dvachmovie.pipe.moviestorage.SetMovieListChangedListenerPipe
import dvachmovie.pipe.settingsstorage.PutBoardPipe
import dvachmovie.pipe.settingsstorage.PutCurrentBaseUrlPipe
import dvachmovie.storage.OnMovieChangedListener
import dvachmovie.storage.OnMovieListChangedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartActivityVM @Inject constructor() : ViewModel() {
    val initText = MutableLiveData("Preparing...")
    val version = MutableLiveData<String>(BuildConfig.VERSION_NAME)

    @Inject
    lateinit var deleteOldMoviesPipe: DeleteOldMoviesPipe

    @Inject
    lateinit var putCurrentBaseUrlPipe: PutCurrentBaseUrlPipe

    @Inject
    lateinit var putBoardPipe: PutBoardPipe

    @Inject
    lateinit var eraseMovieStorage: EraseMovieStoragePipe

    @Inject
    lateinit var setMovieListChangedListenerPipe: SetMovieListChangedListenerPipe

    @Inject
    lateinit var setMovieChangedListenerPipe: SetMovieChangedListenerPipe

    suspend fun setBaseUrl(baseUrl: String, board: String) {
        putCurrentBaseUrlPipe.execute(baseUrl)
        putBoardPipe.execute(board)
        setMovieListChangedListenerPipe.execute(object : OnMovieListChangedListener {
            override fun onListChanged(movies: List<Movie>) = Unit
        })
        setMovieChangedListenerPipe.execute(object : OnMovieChangedListener {
            override fun onMovieChanged(movie: Movie) = Unit
        })
        eraseMovieStorage.execute(Unit)
    }

    suspend fun removeOldMovies() {
        withContext(Dispatchers.IO) {
            deleteOldMoviesPipe.execute(Unit)
        }
    }
}
