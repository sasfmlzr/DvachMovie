package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsStorage.GetIsLoadingEveryTimeUseCase
import dvachmovie.usecase.utils.ShuffleMoviesUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetIsLoadingEveryTimePipeTest {

    @InjectMocks
    lateinit var pipe: GetIsLoadingEveryTimePipe

    @Mock
    lateinit var useCase: GetIsLoadingEveryTimeUseCase

    @Test
    fun `Happy pass`() {
        given(useCase.execute(Unit)).willReturn(false)
        Assert.assertEquals(false, pipe.execute(Unit))
    }
}
