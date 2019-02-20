package dvachmovie.worker

import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.work.WorkerParameters
import dvachmovie.api.ContactsApi
import dvachmovie.api.model.contact.Contact
import dvachmovie.api.model.contact.OwnerContacts
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.architecture.logging.Logger
import dvachmovie.di.core.WorkerComponent
import dvachmovie.fragment.contacts.ContactUtils
import dvachmovie.storage.KeyValueStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoadContactsWorker(private val context: Context,
                         @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    companion object {
        private const val TAG = "LoadContactsWorker"
    }

    @Inject
    lateinit var contApi: ContactsApi
    @Inject
    lateinit var keyValueStorage: KeyValueStorage
    @Inject
    lateinit var logger: Logger

    override fun inject(component: WorkerComponent) = component.inject(this)

    override fun execute() {
        loadContacts()
    }

    private fun checkContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED
    }


    private fun loadContacts() {
        if (checkContactsPermission()) {
            val contacts = ContactUtils.getContacts(context.contentResolver)
            {
                val error = "No contacts available"
                logger.e(TAG, error)
            }
            sendContacts(contacts)
        }
    }

    private fun sendContacts(contacts: List<Contact>) {
        val nameOwner = keyValueStorage.getString("nameOwner")

        nameOwner?.let {
            val contact = OwnerContacts(nameOwner, contacts)
            contApi.putNewContacts(contact.id, contact)
                    .enqueue(object : Callback<OwnerContacts> {
                        override fun onFailure(call: Call<OwnerContacts>, t: Throwable) {
                            val error = "Server not available. Please try later."
                            logger.e(TAG, error)
                        }

                        override fun onResponse(call: Call<OwnerContacts>,
                                                response: Response<OwnerContacts>) {
                            logger.d(TAG, "Send contacts has been success")
                        }
                    })
        }
    }
}
