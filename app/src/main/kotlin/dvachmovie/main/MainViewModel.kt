package dvachmovie.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

class MainViewModel (application: Application) : AndroidViewModel(application) {
    val uriMovie: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }



    fun loadUri(link: String){
        uriMovie.value = link
    }
}
