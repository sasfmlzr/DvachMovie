package dvachmovie.architecture

import kotlinx.coroutines.CoroutineScope

interface ScopeProvider {

    val uiScope: CoroutineScope

    val ioScope: CoroutineScope
}
