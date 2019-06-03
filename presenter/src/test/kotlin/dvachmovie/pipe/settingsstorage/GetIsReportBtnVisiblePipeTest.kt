package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsStorage.GetIsReportBtnVisibleUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetIsReportBtnVisiblePipeTest {

    @InjectMocks
    lateinit var pipe: GetIsReportBtnVisiblePipe

    @Mock
    lateinit var useCase: GetIsReportBtnVisibleUseCase

    @Test
    fun `Happy pass`() {
        given(useCase.execute(Unit)).willReturn(false)
        Assert.assertEquals(false, pipe.execute(Unit))
    }
}
