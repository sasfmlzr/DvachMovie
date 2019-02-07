package dvachmovie.fragment.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.checkSelfPermission
import dvachmovie.PERMISSIONS_REQUEST_READ_CONTACTS
import dvachmovie.api.ContactsApi
import dvachmovie.api.model.contact.OwnerContacts
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentContactsBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ContactsFragment : BaseFragment<ContactsVM,
        FragmentContactsBinding>(ContactsVM::class.java) {

    private lateinit var text: TextView

    private val nameOwner = "Alexey"

    @Inject
    lateinit var contApi: ContactsApi

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentContactsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadContacts()

        text = binding.listContacts

        binding.getAllContacts.setOnClickListener {
            contApi.getAllOwnerContacts().enqueue(object : Callback<List<OwnerContacts>> {
                override fun onFailure(call: Call<List<OwnerContacts>>, t: Throwable) {
                    text.text = t.message
                }

                override fun onResponse(call: Call<List<OwnerContacts>>, response: Response<List<OwnerContacts>>) {
                    text.text = response.body().toString()
                }

            })
        }
        binding.getOneContacts.setOnClickListener {
            contApi.getOwnerById(nameOwner).enqueue(object : Callback<OwnerContacts> {
                override fun onFailure(call: Call<OwnerContacts>, t: Throwable) {
                    text.text = t.message
                }

                override fun onResponse(call: Call<OwnerContacts>, response: Response<OwnerContacts>) {
                    text.text = response.body().toString()
                }

            })
        }
        binding.newContacts.setOnClickListener {
            val contact = OwnerContacts(nameOwner, viewModel.contacts)
            contApi.putContacts(contact).enqueue(object : Callback<OwnerContacts> {
                override fun onFailure(call: Call<OwnerContacts>, t: Throwable) {
                    text.text = t.message
                }

                override fun onResponse(call: Call<OwnerContacts>, response: Response<OwnerContacts>) {
                    text.text = response.body().toString()
                }

            })
        }
        binding.putContacts.setOnClickListener {
            val contact = OwnerContacts(nameOwner, viewModel.contacts)
            contApi.putNewContacts(contact.id, contact).enqueue(object : Callback<OwnerContacts> {
                override fun onFailure(call: Call<OwnerContacts>, t: Throwable) {
                    text.text = t.message
                }

                override fun onResponse(call: Call<OwnerContacts>, response: Response<OwnerContacts>) {
                    text.text = response.body().toString()
                }
            })
        }
    }

    private fun loadContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(context!!,
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS)
            //callback onRequestPermissionsResult
        } else {
            ContactUtils.getContacts(activity!!.contentResolver)
            {
                extensions.showMessage("No contacts available")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                extensions.showMessage("Permission must be granted")
            }
        }
    }
}
