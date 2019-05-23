package dvachmovie

import dvachmovie.architecture.ScopeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class LocalScopeProvider @Inject constructor(): ScopeProvider {

    override val uiScope = CoroutineScope(Dispatchers.Main)

    override val ioScope = CoroutineScope(Dispatchers.IO)
}
