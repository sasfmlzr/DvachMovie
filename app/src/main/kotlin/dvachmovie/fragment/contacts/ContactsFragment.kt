package dvachmovie.fragment.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.checkSelfPermission
import dvachmovie.api.RetrofitSingleton
import dvachmovie.api.model.contact.Contact
import dvachmovie.api.model.contact.OwnerContacts
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentContactsBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactsFragment : BaseFragment<ContactsVM,
        FragmentContactsBinding>(ContactsVM::class.java) {

    private lateinit var text: TextView

    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100

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

        val contApi = RetrofitSingleton.getContactsApi()

        binding.getAllContacts.setOnClickListener {
            contApi!!.getAllOwnerContacts().enqueue(object : Callback<List<OwnerContacts>>{
                override fun onFailure(call: Call<List<OwnerContacts>>, t: Throwable) {
                    text.text = t.message }

                override fun onResponse(call: Call<List<OwnerContacts>>, response: Response<List<OwnerContacts>>) {
                    text.text = response.body().toString() }

            })
        }
        binding.getOneContacts.setOnClickListener {
            contApi!!.getOwnerById("Alexey").enqueue(object : Callback<OwnerContacts>{
                override fun onFailure(call: Call<OwnerContacts>, t: Throwable) {
                    text.text = t.message }

                override fun onResponse(call: Call<OwnerContacts>, response: Response<OwnerContacts>) {
                    text.text = response.body().toString() }

            })
        }
        binding.newContacts.setOnClickListener {
            val contact = OwnerContacts("Alexey", mutableListOf(Contact("asdas", "2222")))
            contApi!!.putContacts(contact).enqueue(object : Callback<OwnerContacts>{
                override fun onFailure(call: Call<OwnerContacts>, t: Throwable) {
                    text.text = t.message }

                override fun onResponse(call: Call<OwnerContacts>, response: Response<OwnerContacts>) {
                    text.text = response.body().toString() }

            })
        }
        binding.putContacts.setOnClickListener {
            val contact = OwnerContacts("Alexey", mutableListOf(Contact("asdas", "3333")))
            contApi!!.putNewContacts("Alexey", contact).enqueue(object : Callback<OwnerContacts>{
                override fun onFailure(call: Call<OwnerContacts>, t: Throwable) {
                    text.text = t.message }

                override fun onResponse(call: Call<OwnerContacts>, response: Response<OwnerContacts>) {
                    text.text = response.body().toString() }

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
            getContacts()
            val ownerContacts = OwnerContacts("Alexey Homa", binding.viewModel!!.contacts)
            binding.listContacts.text = ownerContacts.toString()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                //  toast("Permission must be granted in order to display contacts information")
            }
        }
    }

    @SuppressLint("Recycle")
    private fun getContacts() {
        val resolver: ContentResolver = activity!!.contentResolver
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                null)!!

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = (cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                if (phoneNumber > 0) {
                    val cursorPhone = activity!!.contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)!!

                    if (cursorPhone.count > 0) {
                        while (cursorPhone.moveToNext()) {
                            val phoneNumValue = cursorPhone.getString(
                                    cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            binding.viewModel!!.contacts.add(Contact(name, phoneNumValue))
                        }
                    }
                    cursorPhone.close()
                }
            }
        } else {
            //   toast("No contacts available!")
        }
        cursor.close()
    }
}
