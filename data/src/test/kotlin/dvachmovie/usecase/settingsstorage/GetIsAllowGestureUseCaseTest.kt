package dvachmovie.usecase.settingsstorage

import dvachmovie.TestException
import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.settingsStorage.GetIsAllowGestureUseCase
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
class GetIsAllowGestureUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetIsAllowGestureUseCase

    @Mock
    lateinit var settingsStorage: SettingsStorage

    @Test
    fun `Happy pass`() {
        runBlocking {
            given(settingsStorage.isAllowGesture()).willReturn(CompletableDeferred(false))
            Assert.assertEquals(false, useCase.execute(Unit))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(settingsStorage.isAllowGesture()).willThrow(TestException())
            useCase.execute(Unit)
        }
    }
}