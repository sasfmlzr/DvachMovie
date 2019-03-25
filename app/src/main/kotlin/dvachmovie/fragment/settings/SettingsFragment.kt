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
import dvachmovie.R
import dvachmovie.api.ContactsApi
import dvachmovie.api.model.TypicalResponseItem
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.architecture.base.PermissionsCallback
import dvachmovie.databinding.FragmentSettingsBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
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
        FragmentSettingsBinding>(SettingsVM::class), PermissionsCallback {

    @Inject
    lateinit var settingsStorage: SettingsStorage
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

    private fun setUpToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun configureVM() {
        viewModel.prepareLoading.observe(this, Observer {
            settingsStorage.putLoadingEveryTime(it)
        })

        viewModel.onCleanDB.observe(this, Observer {
            if (it) {
                WorkerManager.deleteAllInDB(this) {
                    movieRepository.getMovies().value = mutableListOf()
                    router.navigateSettingsToStartFragment()
                }
            }
        })

        viewModel.onChangeBoard.observe(this, Observer {
            if (it) {
                movieRepository.getMovies().value = mutableListOf()
                router.navigateSettingsToStartFragment()
            }
        })

        viewModel.getContactClick = { name ->
            checkUniqueName(name)
        }
    }

    private fun checkUniqueName(nameOwner: String) {
        if (nameOwner.length >= 4) {
            contApi.checkUniqueContacts(nameOwner).enqueue(object : Callback<TypicalResponseItem> {
                override fun onFailure(call: Call<TypicalResponseItem>, t: Throwable) {
                    extensions.showMessage(t.message!!)
                }

                override fun onResponse(call: Call<TypicalResponseItem>,
                                        response: Response<TypicalResponseItem>) {
                    if (response.isSuccessful) {
                        keyValueStorage.putString("nameOwner", nameOwner)
                        viewModel.releaseDialog()
                        configureButton()
                        return
                    } else {
                        extensions.showMessage(JSONObject(response.errorBody()?.string())
                                .getString("message"))
                    }
                }
            })
        } else {
            extensions.showMessage("Choose another name")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureButton()
    }

    private fun configureButton() {
        buttonRequestContactPermission.setOnClickListener {
            runtimePermissions.request(Manifest.permission.READ_CONTACTS)
        }
        buttonRequestLocationPermission.setOnClickListener {
            runtimePermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
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
            WorkerManager.loadContactsToNetwork()
            WorkerManager.loadLocationToNetwork()
        }
    }

    override fun onPermissionsGranted(permissions: List<String>) {
        permissions.forEach {
            when (it) {
                Manifest.permission.READ_CONTACTS -> configureButton()
                Manifest.permission.ACCESS_FINE_LOCATION -> configureButton()
            }
        }
    }
}
