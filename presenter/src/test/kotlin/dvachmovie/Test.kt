package dvachmovie

import dvachmovie.db.data.NullMovie
import dvachmovie.db.model.ThreadEntity
import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test

class Test {

    @Test
    fun wtf() {
        val movie0 = NullMovie("asdas0", isHidden = false, thread = 3)
        val movie1 = NullMovie("asdas1", isHidden = false, thread = 1)
        val movie2 = NullMovie("asdas2", isHidden = false, thread = 1)
        val movie3 = NullMovie("asdas3", isHidden = false, thread = 2)
        val movie4 = NullMovie("asdas4", isHidden = false, thread = 1)
        val movie5 = NullMovie("asdas5", isHidden = false, thread = 3)

        val currentMovie = movie1

        var resultCurrentMovie = NullMovie()
        val movies = listOf(movie0, movie1, movie2, movie3, movie4, movie5)

        val hiddenThreads = listOf(ThreadEntity(1, date = LocalDateTime(),
                isHidden = true,
                nameThread = "dasd"))

        var isCurrentMovieCatched = false
        var isPrewThreadEqual = false
        val resultMovieList = movies.filterNot { movie ->
            var isThreadEqual = false

            hiddenThreads.forEach { thread ->
                if (thread.thread == movie.thread) {

                    isThreadEqual = true
                }
            }

            if (currentMovie.movieUrl == movie.movieUrl) {
                isCurrentMovieCatched = true
            }

            if (isPrewThreadEqual && !isThreadEqual && isCurrentMovieCatched) {
                resultCurrentMovie = movie
                isCurrentMovieCatched = false
            }
            isPrewThreadEqual = isThreadEqual
            isThreadEqual
        }

        val expectedList = listOf(movie0, movie3, movie5)
        val expectedMovie = movie3

        Assert.assertEquals(expectedList, resultMovieList)
        Assert.assertEquals(expectedMovie, resultCurrentMovie)
    }
}