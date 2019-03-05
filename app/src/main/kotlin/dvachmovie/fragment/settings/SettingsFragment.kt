package dvachmovie.fragment.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.Observer
import dvachmovie.PERMISSIONS_REQUEST_LOCATION
import dvachmovie.PERMISSIONS_REQUEST_READ_CONTACTS
import dvachmovie.R
import dvachmovie.api.ContactsApi
import dvachmovie.api.model.TypicalResponseItem
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentSettingsBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.repository.local.MovieCache
import dvachmovie.repository.local.MovieRepository
import dvachmovie.storage.KeyValueStorage
import dvachmovie.storage.SettingsStorage
import dvachmovie.worker.WorkerManager
import kotlinx.android.synthetic.main.fragment_settings.*
import org.json.JSONObject
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
    lateinit var keyValueStorage: KeyValueStorage
    @Inject
    lateinit var contApi: ContactsApi

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        setUpToolbar()

        configureVM()

        return binding.root
    }

    private fun configureVM() {
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

        viewModel.getContactClick = { name ->
            checkUniqueName(name)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureButton()
    }

    private fun checkUniqueName(nameOwner: String) {
        if (nameOwner.length >= 4) {
            contApi.checkUniqueContacts(nameOwner).enqueue(object : Callback<TypicalResponseItem> {
                override fun onFailure(call: Call<TypicalResponseItem>, t: Throwable) {
                    extensions.showMessage(t.message!!)
                }

                override fun onResponse(call: Call<TypicalResponseItem>,
                                        response: Response<TypicalResponseItem>) {
                    if(response.isSuccessful) {
                        keyValueStorage.putString("nameOwner", nameOwner)
                        viewModel.releaseDialog()
                        configureButton()
                        return
                    } else {
                        extensions.showMessage(JSONObject(response.
                                errorBody()?.string())
                                .getString("message"))
                    }
                }
            })
        } else {
            extensions.showMessage("Choose another name")
        }
    }

    private fun setUpToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun configureButton() {
        buttonRequestContactPermission.setOnClickListener {
            requestContactPermission()
        }
        buttonRequestLocationPermission.setOnClickListener {
            requestLocationPermission()
        }

        var step1 = false
        if (keyValueStorage.getString("nameOwner") != null) {
            buttonSendName.visibility = View.GONE
            textStep1.visibility = View.GONE
            step1 = true
        }
        var step2 = false
        if (checkSelfPermission(context!!,
                        Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED) {
            buttonRequestContactPermission.visibility = View.GONE
            textStep2.visibility = View.GONE
            step2 = true
        }
        var step3 = false
        if (checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            buttonRequestLocationPermission.visibility = View.GONE
            textStep3.visibility = View.GONE
            step3 = true
        }
        if (step1 && step2 && step3) {
            textStepsOverview.text = getString(R.string.unlocked)
        }
    }

    private fun requestContactPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS)
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            configureButton()
        } else if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            configureButton()
        } else {
            extensions.showMessage("Permission must be granted")
        }
    }
}
