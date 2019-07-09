package dvachmovie.architecture.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import dvachmovie.architecture.logging.Logger

class MyServer (val logger: Logger) : LifecycleObserver{
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(source: LifecycleOwner, event: Lifecycle.Event ){
        logger.d(source.javaClass.name, "$event")
    }
}
