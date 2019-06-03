package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsStorage.GetIsListBtnVisibleUseCase
import dvachmovie.usecase.utils.ShuffleMoviesUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetIsListBtnVisiblePipeTest {

    @InjectMocks
    lateinit var pipe: GetIsListBtnVisiblePipe

    @Mock
    lateinit var useCase: GetIsListBtnVisibleUseCase

    @Test
    fun `Happy pass`() {
        given(useCase.execute(Unit)).willReturn(false)
        Assert.assertEquals(false, pipe.execute(Unit))
    }
}
