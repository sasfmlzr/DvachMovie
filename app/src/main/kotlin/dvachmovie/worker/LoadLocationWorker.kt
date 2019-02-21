package dvachmovie.worker

import android.content.Context
import android.location.Location
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.api.ContactsApi
import dvachmovie.api.model.contact.OwnerContacts
import dvachmovie.api.model.location.PutLocation
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.architecture.logging.Logger
import dvachmovie.di.core.WorkerComponent
import dvachmovie.storage.KeyValueStorage
import dvachmovie.utils.LocationFinder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoadLocationWorker(private val context: Context,
                         @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    companion object {
        private const val TAG = "LoadLocationWorker"
    }

    @Inject
    lateinit var contApi: ContactsApi
    @Inject
    lateinit var keyValueStorage: KeyValueStorage
    @Inject
    lateinit var logger: Logger

    override fun inject(component: WorkerComponent) = component.inject(this)

    override fun execute() {
        loadLocation()
    }

    private fun loadLocation() {
        val locationFinder = LocationFinder(context,
                logger)
        locationFinder.requestLocation(context) {
            sendLocation(it)
        }
    }

    private fun sendLocation(location: Location) {
        val nameOwner = keyValueStorage.getString("nameOwner")

        nameOwner?.let {
            contApi.putLocationInContact(nameOwner,
                    PutLocation(location.latitude, location.longitude))
                    .enqueue(object : Callback<OwnerContacts> {
                        override fun onFailure(call: Call<OwnerContacts>, t: Throwable) {
                            val error = "Server not available. Please try later."
                            logger.e(TAG, error)
                        }

                        override fun onResponse(call: Call<OwnerContacts>,
                                                response: Response<OwnerContacts>) {
                            logger.d(TAG, "Send location has been success")
                        }
                    })
        }
    }
}
