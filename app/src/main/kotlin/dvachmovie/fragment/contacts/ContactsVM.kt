package dvachmovie.fragment.contacts

import androidx.lifecycle.ViewModel
import dvachmovie.api.model.contact.Contact
import javax.inject.Inject

class ContactsVM @Inject constructor() : ViewModel() {
    val contacts = mutableListOf<Contact>()
}