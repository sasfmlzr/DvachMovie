package dvachmovie.usecase.settingsstorage

import dvachmovie.TestException
import dvachmovie.storage.SettingsStorage
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetIsLoadingEveryTimeUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetIsLoadingEveryTimeUseCase

    @Mock
    lateinit var settingsStorage: SettingsStorage

    @Test
    fun `Happy pass`() {
        given(settingsStorage.isLoadingEveryTime()).willReturn(false)
        Assert.assertEquals(false, useCase.execute(Unit))
        runBlocking {
            given(settingsStorage.isLoadingEveryTimeAsync()).willReturn(CompletableDeferred(false))
            Assert.assertEquals(false, useCase.executeAsync(Unit))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(settingsStorage.isLoadingEveryTimeAsync()).willThrow(TestException())
            useCase.executeAsync(Unit)
        }
    }
}
