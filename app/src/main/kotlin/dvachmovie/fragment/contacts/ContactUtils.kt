package dvachmovie.fragment.contacts

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract
import dvachmovie.api.model.contact.Contact

@SuppressLint("Recycle")
class ContactUtils private constructor() {

    companion object {
        fun getContacts(resolver: ContentResolver,
                        notExistsContact: (() -> Unit)): List<Contact> {
            val contactList = mutableListOf<Contact>()

            val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    null)!!

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber = (cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                    if (phoneNumber > 0) {
                        getPhoneNumberForCurrentName(id, resolver).forEach { phone ->
                            contactList.add(Contact(name, phone))
                        }
                    }
                }
            } else {
                notExistsContact()
            }
            cursor.close()
            return contactList
        }

        private fun getPhoneNumberForCurrentName(contactId: String, resolver: ContentResolver): List<String> {
            val phoneList = mutableListOf<String>()
            val cursorPhone = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                    arrayOf(contactId),
                    null)!!

            if (cursorPhone.count > 0) {
                while (cursorPhone.moveToNext()) {
                    val phoneNumValue = cursorPhone.getString(
                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    phoneList.add(phoneNumValue)
                }
            }
            cursorPhone.close()
            return phoneList
        }
    }
}
