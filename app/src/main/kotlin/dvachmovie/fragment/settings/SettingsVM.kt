package dvachmovie.fragment.settings

import android.content.DialogInterface
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.architecture.logging.Logger
import dvachmovie.storage.SettingsStorage
import javax.inject.Inject


class SettingsVM @Inject constructor(
        settingsStorage: SettingsStorage,
        private val logger: Logger
) : ViewModel() {

    companion object {
        private const val TAG = "SettingsVM"
    }

    val prepareLoading = MutableLiveData<Boolean>()

    val onRefreshDB = MutableLiveData<Boolean>()

    /**
     * @return {@code true} if the listener has success,
     *         {@code false} otherwise
     *         */
    lateinit var getContactClick: ((contactName: String) -> Unit)

    init {
        prepareLoading.value = settingsStorage.isLoadingEveryTime()
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
                            logger.d(TAG, "refresh database")
                            onRefreshDB.value = true
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
            }

    val onGetContact =
            View.OnClickListener {
                val login = LinearLayout(it.context)

                val editText = EditText(it.context)

                login.orientation = LinearLayout.VERTICAL


                login.addView(editText)
                val dialog = AlertDialog.Builder(it.context)
                        .setTitle("Confirmation")
                        .setMessage("Give me your data")
                        .setView(login)
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                    (getContactClick(editText.text.toString()))
                }
                this.dialog = dialog
            }

    private var dialog: AlertDialog? = null

    fun releaseDialog (){
        dialog?.dismiss()
        dialog = null
    }

}
