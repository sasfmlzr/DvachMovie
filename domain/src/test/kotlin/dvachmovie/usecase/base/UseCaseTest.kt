package dvachmovie.usecase.base

import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.RuntimeException

class UseCaseTest {

    @Test(expected = RuntimeException::class)
    fun syncTestWasNotImplemented(){
        val useCase = object :UseCase<Unit, Unit>(){}
        useCase.execute(Unit)
    }

    @Test(expected = RuntimeException::class)
    fun asyncTestWasNotImplemented(){
        val useCase = object :UseCase<Unit, Unit>(){}
        runBlocking {
            useCase.executeAsync(Unit)
        }
    }
}