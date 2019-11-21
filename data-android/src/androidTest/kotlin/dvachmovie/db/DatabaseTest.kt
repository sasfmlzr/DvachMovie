package dvachmovie.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dvachmovie.db.model.MovieDao
import dvachmovie.db.model.MovieEntity
import dvachmovie.db.model.ThreadDao
import dvachmovie.db.model.ThreadEntity
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.joda.time.LocalDateTime
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var db: MovieDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var threadDao: ThreadDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, MovieDatabase::class.java).build()
        movieDao = db.movieDao()
        threadDao = db.threadDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun happyPassAndroidMovieData() {
        val baseUrl = "testBaseUrl"
        val testBoard = "testBoard"
        val movie = MovieEntity("movieUrl", "previewUrl", testBoard,
                false, LocalDateTime(), "md5", 1, 5, baseUrl)
        movieDao.insertAll(listOf(movie))
        val newMovie = movieDao.getMoviesFromBoard(baseUrl, testBoard)
        assertThat(newMovie[0], equalTo(movie))
        Assert.assertEquals(newMovie[0].hashCode(), movie.hashCode())
        Assert.assertEquals(newMovie[0].baseUrl, movie.baseUrl)
        Assert.assertNotEquals(false, movie.baseUrl)
    }

    @Test
    @Throws(Exception::class)
    fun happyPassAndroidThreadData() {
        val threadNumber = "10"
        val baseUrl = "testBaseUrl"
        val boardThread = "testThread"
        val thread = ThreadEntity(threadNumber.toLong(), LocalDateTime(), false,
                boardThread, baseUrl)
        threadDao.insertAll(listOf(thread))
        val newThread = threadDao.getThreadByNum(threadNumber, baseUrl)
        assertThat(newThread, equalTo(thread))
        Assert.assertEquals(newThread.hashCode(), thread.hashCode())
        Assert.assertEquals(newThread?.baseUrl, thread.baseUrl)
        Assert.assertNotEquals(false, thread.baseUrl)
    }
}
