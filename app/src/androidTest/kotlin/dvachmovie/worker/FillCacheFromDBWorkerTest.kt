package dvachmovie.worker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import dvachmovie.db.data.Movie
import dvachmovie.di.DaggerAndroidTestApplicationComponent
import dvachmovie.di.RoomModule
import dvachmovie.di.core.ApplicationModule
import dvachmovie.di.core.Injector
import dvachmovie.di.core.MainApplication
import dvachmovie.storage.MovieStorage
import dvachmovie.storage.OnMovieListChangedListener
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class FillCacheFromDBWorkerTest {

    @Inject
    lateinit var storage: MovieStorage

    private lateinit var context: Context

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
        val request = TestListenableWorkerBuilder<FillCacheFromDBWorker>(context)
                .setInputData(Data.Builder().putString("BOARD", "test").build())
                .build()

        storage.onMovieListChangedListener = object : OnMovieListChangedListener {
            override fun onListChanged(movies: List<Movie>) = Unit
        }

        runBlocking {
            val result = request.startWork().get()
            assertThat(result, `is`(ListenableWorker.Result.success()))
        }
    }

    @Test
    fun boardCannotBeNull() {
        val request = TestListenableWorkerBuilder<FillCacheFromDBWorker>(context)
                .build()

        runBlocking {
            val result = request.doWork()
            assertThat(result, `is`(ListenableWorker.Result.failure()))
        }
    }
}
