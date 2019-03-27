package dvachmovie.fragment.settings

import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.architecture.logging.Logger
import dvachmovie.storage.SettingsStorage
import javax.inject.Inject

class SettingsVM @Inject constructor(
        settingsStorage: SettingsStorage,
        logger: Logger
) : ViewModel() {

    companion object {
        private const val TAG = "SettingsVM"
    }

    val prepareLoading = MutableLiveData<Boolean>(settingsStorage.isLoadingEveryTime())

    val onCleanDB = MutableLiveData<Boolean>(false)

    val onChangeBoard = MutableLiveData<Boolean>(false)

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
                            onCleanDB.value = true
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
            }

    val onSetBoard =
            View.OnClickListener {
                val boardMap = hashMapOf<String, String>()
                boardMap["b"] = "Бред"
                boardMap["vg"] = "Video Games General"
                boardMap["mov"] = "Фильмы"
                boardMap["po"] = "Политика"
                boardMap["v"] = "Video Games"
                boardMap["soc"] = "Общение"
                boardMap["mu"] = "Музыка"
                boardMap["mus"] = "Музыканты"

                var checkedItem = boardMap.keys.indexOf(settingsStorage.getBoard())
                AlertDialog.Builder(it.context)
                        .setTitle("Set board")
                        .setSingleChoiceItems(
                                boardMap.values.toTypedArray(),
                                checkedItem
                        ) { _, which ->
                            checkedItem = which
                        }
                        .setPositiveButton("Ok") { _, _ ->
                            if (checkedItem != -1) {
                                settingsStorage.putBoard(boardMap.keys.elementAt(checkedItem))
                                onChangeBoard.value = true
                            }
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
            }
}
