package dvachmovie.fragment.settings

import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.storage.SettingsStorage
import javax.inject.Inject

class SettingsVM @Inject constructor(
        settingsStorage: SettingsStorage
) : ViewModel() {

    val prepareLoading = MutableLiveData<Boolean>()

    val onRefreshDB = MutableLiveData<Boolean>()

    lateinit var getContactClick: (() -> Unit)

    init {
        prepareLoading.value = settingsStorage.getBoolLoadingParam()
        onRefreshDB.value = false
    }

    val onPrepareLoadingClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
                prepareLoading.value = isChecked
            }

    val onRefreshDatabase =
            View.OnClickListener {
                AlertDialog.Builder(it.context)
                        .setTitle("Confirmation")
                        .setMessage("Database will clean")
                        .setPositiveButton("Ok") { _, _ ->
                            println("refresh")
                            onRefreshDB.value = true
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
            }

    val onGetContact =
            View.OnClickListener {
                val editText = EditText(it.context)

                AlertDialog.Builder(it.context)
                        .setTitle("Confirmation")
                        .setMessage("Give me your data")
                        .setView(editText)
                        .setPositiveButton("Ok") { _, _ ->
                            println("refresh")
                            getContactClick()
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
            }
}