package dvachmovie.pipe.android

import dvachmovie.usecase.EraseDBUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EraseDBPipeTest {

    @InjectMocks
    lateinit var pipe: EraseDBPipe

    @Mock
    lateinit var useCase: EraseDBUseCase

    @Test
    fun `Happy pass`() {
        pipe.execute(Unit)
    }
}
