package dvachmovie.pipe.db

import dvachmovie.usecase.moviestorage.MarkCurrentMovieAsPlayedUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MarkCurrentMovieAsPlayedPipeTest {

    @InjectMocks
    lateinit var pipe: MarkCurrentMovieAsPlayedPipe

    @Mock
    lateinit var useCase: MarkCurrentMovieAsPlayedUseCase

    @Test
    fun `Happy pass`() {
        pipe.execute(999)
    }
}
