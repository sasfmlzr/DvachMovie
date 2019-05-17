package dvachmovie

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object ScopeProvider {

    fun getUiScope() = CoroutineScope(Dispatchers.Main)

    fun getIOScope() = CoroutineScope(Dispatchers.IO)

}
