package dvachmovie.fragment.settings

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.EditText
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
    lateinit var getContactClick: (contactName: String) -> Unit

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

    val onSendNameContactOwner =
            View.OnClickListener {
                val editText = EditText(it.context)
                val dialog = AlertDialog.Builder(it.context)
                        .setTitle("Step 1")
                        .setMessage("Write your name")
                        .setView(editText)
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { view ->
                    (getContactClick(editText.text.toString()))
                    hideKeyboard(view)

                }
                this.dialog = dialog
            }

    private var dialog: AlertDialog? = null

    fun releaseDialog() {
        dialog?.dismiss()
        dialog = null
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context?.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        val token = view.applicationWindowToken
        imm!!.hideSoftInputFromWindow(token, 0)
    }

}
