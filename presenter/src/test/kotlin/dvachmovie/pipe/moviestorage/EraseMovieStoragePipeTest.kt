package dvachmovie.pipe.moviestorage

import dvachmovie.usecase.moviestorage.EraseMovieStorageUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EraseMovieStoragePipeTest {

    @InjectMocks
    lateinit var pipe: EraseMovieStoragePipe

    @Mock
    lateinit var useCase: EraseMovieStorageUseCase

    @Test
    fun `Happy pass`() {
        val str = "https://telegra.ph/pimeyesbot-09-21-"
        var strValue = 500
        var newValue = ""
        repeat(300) {
            newValue = newValue + str+(strValue+it).toString() + "\n"
        }
        pipe.execute(Unit)
    }
}
