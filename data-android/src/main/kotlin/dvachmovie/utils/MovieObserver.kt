package dvachmovie.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dvachmovie.db.data.Movie

interface MovieObserver {
    fun observeDB(lifecycleOwner: LifecycleOwner)
    fun observeDB(lifecycleOwner: LifecycleOwner, observer: Observer<List<Movie>>)
}
