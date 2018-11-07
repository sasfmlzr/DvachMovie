package dvachmovie.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val uriMovie: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

    fun loadUri(links: List<String>) {
        uriMovie.value = links
    }
}
