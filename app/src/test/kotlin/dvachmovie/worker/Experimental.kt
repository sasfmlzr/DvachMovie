package dvachmovie.worker

import android.net.Uri
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*
import kotlin.system.measureNanoTime

@RunWith(RobolectricTestRunner::class)
class Experimental {
    private val list = mutableListOf<String>()
    private val uri = "https://gist.github.com/sasfmlzr/e4ccac8e5e6d9a9f65146674035e615e"

    private val countIterations = 3

    @Before
    fun setUp() {
        for (i in 0 until 5000) {
            list.add(uri + i.toString())
        }
    }

    @Test
    fun mapUri() {
        var summ: Long = 0L

        for (i in 0 until countIterations) {
            val timeElapsed = measureNanoTime {
                val urlVideo: List<Uri> = list.map { value -> Uri.parse(value) }
            }
            summ = +timeElapsed
        }

        summ /= countIterations

        println("mapUri is $summ")
    }

    @Test
    fun forEachUri() {
        var summ: Long = 0L

        for (i in 0 until countIterations) {
            var urlVideo = mutableListOf<Uri>()

            val timeElapsed = measureNanoTime {
                list.forEach { value -> urlVideo.add(Uri.parse(value)) }
            }
            summ = +timeElapsed
        }

        summ /= countIterations

        println("forEachUri is $summ")
    }

    @Test
    fun copy() {
        var summ: Long = 0L

        for (i in 0 until countIterations) {
            val result = ArrayList<String>(list)

            val timeElapsed = measureNanoTime {
                Collections.copy(result, list)
            }
            summ = +timeElapsed
        }

        summ /= countIterations

        println("copy is $summ")
    }

    @Test
    fun toMutableList() {
        var summ: Long = 0L

        for (i in 0 until countIterations) {
            var result = mutableListOf<String>()

            val timeElapsed = measureNanoTime {
                result = list.toMutableList()
                list.map { result.add(it) }
            }
            summ = +timeElapsed
        }
        summ /= countIterations

        println("toMutableList is $summ")
    }


    @Test
    fun mapAsAdd() {
        var summ: Long = 0L

        for (i in 0 until countIterations) {
            val result = mutableListOf<String>()

            val timeElapsed = measureNanoTime {

                list.map { result.add(it) }
            }
            summ = +timeElapsed
        }
        summ /= countIterations

        println("mapAsAdd is $summ")
    }

    @Test
    fun mapAsEqual() {
        var summ: Long = 0L

        for (i in 0 until countIterations) {
            var result = listOf<String>()
            val timeElapsed = measureNanoTime {
                result = list.map { it -> it }
            }
            summ = +timeElapsed
        }
        summ /= countIterations

        println("mapAsEqual is $summ")
    }


    @Test
    fun forTest() {
        var summ: Long = 0L

        for (i in 0 until countIterations) {
            val result = mutableListOf<String>()
            val timeElapsed = measureNanoTime {
                for (it in list) {
                    result.add(it)
                }
            }
            summ = +timeElapsed
        }
        summ /= countIterations

        println("forTest is $summ")
    }

    @Test
    fun foreachTest() {
        var summ: Long = 0L

        for (i in 0 until countIterations) {
            val result = mutableListOf<String>()
            val timeElapsed = measureNanoTime {
                list.forEach { result.add(it) }
            }
            summ = +timeElapsed
        }
        summ /= countIterations

        println("foreachTest is $summ")
    }
}