package dvachmovie.fragment.contacts

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import dvachmovie.api.model.contact.Contact

@SuppressLint("Recycle")
class ContactUtils private constructor() {

    companion object {
        fun getContacts(resolver: ContentResolver,
                        notExistsContact: (() -> Unit)): List<Contact> {
            val contactList = mutableListOf<Contact>()

            val cursor = resolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    null)!!

            if (cursor.count > 0) {
                contactList.addAll(getContactFromCursor(cursor, resolver))
            } else {
                notExistsContact()
            }
            cursor.close()
            return deleteDuplicates(contactList)
        }

        private fun getContactFromCursor(cursor: Cursor,
                                         resolver: ContentResolver): List<Contact> {
            val contactList = mutableListOf<Contact>()
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = (cursor.getString(
                        cursor.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()
                if (phoneNumber > 0) {
                    getPhoneNumberForCurrentName(id, resolver).forEach { phone ->
                        contactList.add(Contact(name, phone))
                    }
                }
            }
            return contactList
        }

        private fun getPhoneNumberForCurrentName(contactId: String,
                                                 resolver: ContentResolver): List<String> {
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

        private fun deleteDuplicates(contacts: List<Contact>): List<Contact> {
            val result = hashSetOf<Contact>()

            contacts.forEach {
                var phone = PhoneNumberUtils.normalizeNumber(it.phone)

                if (phone.length > 10 && phone[0].toString() == "8" ||
                        phone[0].toString() == "+" && phone[1].toString() == "7") {
                    phone = PhoneNumberUtils
                            .formatNumberToE164(phone, "RU")
                }
                result.add(Contact(it.name, phone))
            }
            return result.toList()
        }
    }
}
