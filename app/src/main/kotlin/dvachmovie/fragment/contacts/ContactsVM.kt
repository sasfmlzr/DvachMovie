package dvachmovie.fragment.contacts

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ContactsVM @Inject constructor() : ViewModel() {
    val contacts = hashMapOf<String, String>()
}