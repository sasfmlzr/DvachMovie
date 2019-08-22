package dvachmovie.worker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import dvachmovie.db.data.Movie
import dvachmovie.db.data.NullMovie
import dvachmovie.di.DaggerAndroidTestApplicationComponent
import dvachmovie.di.RoomModule
import dvachmovie.di.core.ApplicationModule
import dvachmovie.di.core.Injector
import dvachmovie.di.core.MainApplication
import dvachmovie.storage.MovieStorage
import dvachmovie.storage.OnMovieChangedListener
import dvachmovie.storage.OnMovieListChangedListener
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class MarkThreadAsHiddenDBWorkerTest {

    private lateinit var context: Context

    @Inject
    lateinit var storage: MovieStorage

    @Before
    fun setup() {
        val app = ApplicationProvider.getApplicationContext<MainApplication>()
        context = ApplicationProvider.getApplicationContext()

        val testAppComponent = DaggerAndroidTestApplicationComponent.builder()
                .applicationModule(ApplicationModule(app))
                .roomModule(RoomModule(app))
                .build()

        Injector.component = testAppComponent
        testAppComponent.testComponent().inject(this)
    }

    @Test
    fun happyPass() {
        val request = TestListenableWorkerBuilder<MarkThreadAsHiddenDBWorker>(context)
                .build()

        storage.onMovieListChangedListener = object : OnMovieListChangedListener {
            override fun onListChanged(movies: List<Movie>) {
            }
        }
        storage.onMovieChangedListener = object : OnMovieChangedListener {
            override fun onMovieChanged(movie: Movie) {
            }
        }
        storage.setMovieListAndUpdate(listOf(NullMovie()))

        runBlocking {
            val result = request.doWork()
            ViewMatchers.assertThat(result, CoreMatchers.`is`(ListenableWorker.Result.success()))
        }
    }

    @Test
    fun somethingWasWrong() {
        val request = TestListenableWorkerBuilder<MarkThreadAsHiddenDBWorker>(context)
                .build()

        runBlocking {
            val result = request.doWork()
            ViewMatchers.assertThat(result, CoreMatchers.`is`(ListenableWorker.Result.failure()))
        }
    }
}
