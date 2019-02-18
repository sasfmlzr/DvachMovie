package dvachmovie.fragment.settings

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.Observer
import dvachmovie.PERMISSIONS_REQUEST_LOCATION
import dvachmovie.PERMISSIONS_REQUEST_READ_CONTACTS
import dvachmovie.api.ContactsApi
import dvachmovie.api.model.contact.Contact
import dvachmovie.api.model.contact.OwnerContacts
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentSettingsBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.fragment.contacts.ContactUtils
import dvachmovie.repository.local.MovieCache
import dvachmovie.repository.local.MovieRepository
import dvachmovie.service.LocationService
import dvachmovie.storage.SettingsStorage
import dvachmovie.worker.WorkerManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SettingsFragment : BaseFragment<SettingsVM,
        FragmentSettingsBinding>(SettingsVM::class) {

    @Inject
    lateinit var settingsStorage: SettingsStorage
    @Inject
    lateinit var movieCaches: MovieCache
    @Inject
    lateinit var movieRepository: MovieRepository
    @Inject
    lateinit var contApi: ContactsApi

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        viewModel.prepareLoading.observe(this, Observer {
            settingsStorage.putLoadingEveryTime(it)
        })

        viewModel.onRefreshDB.observe(this, Observer {
            if (it) {
                WorkerManager.deleteAllInDB(this) {
                    movieCaches.movieList.value = mutableListOf()
                    movieRepository.getMovies().value = mutableListOf()
                    router.navigateSettingsToStartFragment()
                }
            }
        })

        viewModel.getContactClick = {name ->
            //  router.navigateSettingsToContactsFragment()
            nameOwner = name
            requestLocationPermission()
            requestContactPermission()
        }

        setUpToolbar()

        return binding.root
    }

    private fun setUpToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private lateinit var nameOwner : String

    private fun requestContactPermission() {
        if (checkSelfPermission(context!!,
                        Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS)
            //callback onRequestPermissionsResult
        } else {
            loadContacts()
        }
    }

    private fun requestLocationPermission() {

        if (checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION)
            //callback onRequestPermissionsResult
        } else {
            loadLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadLocation()
        } else if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadContacts()
        } else {
            extensions.showMessage("Permission must be granted")
        }
    }

    private fun loadLocation() {
        viewModel.releaseDialog()

        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                // Called when a new location is found by the network location provider.
                //   makeUseOfNewLocation(location)
                print("asdasd")

            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }
        }
        val locationService = LocationService.getLocationManager(context!!, locationListener)
    }

    private fun loadContacts() {
        val contacts = ContactUtils.getContacts(activity!!.contentResolver)
        {
            extensions.showMessage("No contacts available")
        }
        sendContacts(contacts)
    }

    private fun sendContacts(contacts: List<Contact>) {
        val contact = OwnerContacts(nameOwner, contacts)
        contApi.putNewContacts(contact.id, contact).enqueue(object : Callback<OwnerContacts> {
            override fun onFailure(call: Call<OwnerContacts>, t: Throwable) {
                val error = "Server not available. Please try later."
                extensions.showMessage(error)
            }

            override fun onResponse(call: Call<OwnerContacts>, response: Response<OwnerContacts>) {
            }
        })
    }
}
