package dvachmovie.pipe.moviestorage

import dvachmovie.pipe.moviestorage.EraseMovieStoragePipe
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
        pipe.execute(Unit)
    }
}
