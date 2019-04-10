package experimental

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.Test

class Exp {

    private var job = 0

    val mutex = Mutex()


    val jobd = SupervisorJob()                               // (1)
    val scope = CoroutineScope(Dispatchers.Unconfined + jobd)

    @Test
    fun coroutines() {

        for (x in 0 until 10000) {
            runJob(scope

            )
        }
        Thread.sleep(30000)

        print("")
    }


    fun runJob(scope: CoroutineScope) {

        scope.launch {

            Thread.sleep(1000)
            mutex.withLock {
                job++
                println("[${Thread.currentThread().name}] $job running")
            }
        }
    }

}