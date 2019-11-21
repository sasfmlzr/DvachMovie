package dvachmovie.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dvachmovie.db.model.MovieDao
import dvachmovie.db.model.MovieEntity
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.joda.time.LocalDateTime
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var movieDao: MovieDao
    private lateinit var db: MovieDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, MovieDatabase::class.java).build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val baseUrl = "testBaseUrl"
        val testBoard = "testBoard"
        val movie = MovieEntity("movieUrl", "previewUrl", testBoard,
                false, LocalDateTime(), "md5", 1, 5, baseUrl)
        movieDao.insertAll(listOf(movie))
        val newMovie = movieDao.getMoviesFromBoard(baseUrl, testBoard)
        assertThat(newMovie[0], equalTo(movie))
        println("Movie test finished")
    }
}
