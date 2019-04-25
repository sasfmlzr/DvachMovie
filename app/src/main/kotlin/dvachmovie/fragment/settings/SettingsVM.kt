package dvachmovie.fragment.settings

import android.content.Context
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.BuildConfig
import dvachmovie.architecture.logging.Logger
import dvachmovie.storage.SettingsStorage
import javax.inject.Inject
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import android.widget.TextView
import dvachmovie.R


class SettingsVM @Inject constructor(
        private val settingsStorage: SettingsStorage,
        logger: Logger
) : ViewModel() {

    companion object {
        private const val TAG = "SettingsVM"
    }

    val prepareLoading = MutableLiveData<Boolean>(settingsStorage.isLoadingEveryTime())

    val onCleanDB = MutableLiveData<Boolean>(false)

    val onChangeBoard = MutableLiveData<Boolean>(false)

    val version = MutableLiveData<String>(BuildConfig.VERSION_NAME)

    val onPrepareLoadingClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
                prepareLoading.value = isChecked
            }

    val isGestureEnabled = MutableLiveData<Boolean>(settingsStorage.isAllowGesture())

    val onGestureLoadingClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
                isGestureEnabled.value = isChecked
            }

    val onRefreshDatabase =
            View.OnClickListener {
                AlertDialog.Builder(it.context, R.style.AlertDialogStyle)
                        .setTitle("Confirmation")
                        .setMessage("Database will clean")
                        .setPositiveButton("Ok") { _, _ ->
                            logger.d(TAG, "refresh database")
                            onCleanDB.value = true
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
            }

    val onSetPopularBoard =
            View.OnClickListener {
                showChangeBoardDialog(it.context, Boards.popularMap)
            }

    val onSetThemeBoard =
            View.OnClickListener {
                showChangeBoardDialog(it.context, Boards.themeMap)
            }

    val onSetCreationBoard =
            View.OnClickListener {
                showChangeBoardDialog(it.context, Boards.creationMap)
            }

    val onSetPolNewsBoard =
            View.OnClickListener {
                showChangeBoardDialog(it.context, Boards.politycsAndNewsMap)
            }

    val onSetTechSoftBoard =
            View.OnClickListener {
                showChangeBoardDialog(it.context, Boards.techniqueAndSoftwareMap)
            }

    val onSetGamesBoard =
            View.OnClickListener {
                showChangeBoardDialog(it.context, Boards.gamesMap)
            }

    val onSetJapanBoard =
            View.OnClickListener {
                showChangeBoardDialog(it.context, Boards.japanCultureMap)
            }

    val onSetAdultOtherBoard =
            View.OnClickListener {
                showChangeBoardDialog(it.context, Boards.adultOtherMap)
            }

    val onSetAdultBoard =
            View.OnClickListener {
                showChangeBoardDialog(it.context, Boards.adultMap)
            }

    val onGetProVersion =
            View.OnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("market://details?id=com.dvachmovie.android.pro")
                startActivity(it.context, intent, null)
            }

    val isAllowUnmoderatedContent = MutableLiveData<Boolean>(settingsStorage.isAllowUnmoderatedContent())

    val allowUnmoderatedContent =  View.OnClickListener {
        val textView = TextView(it.context).apply {
            text = context.getString(R.string.unmoderated_content_warning)
        }

        AlertDialog.Builder(it.context, R.style.AlertDialogStyle)
                .setTitle("Allow unmoderated content")
                .setView(textView)
                .setPositiveButton("Ok") { _, _ ->
                    settingsStorage.putIsAllowUnmoderatedContent(true)
                    isAllowUnmoderatedContent.value = true
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .show()
    }

    private fun showChangeBoardDialog(context: Context, boardMap: HashMap<String, String>) {
        var checkedItem = boardMap.keys.indexOf(settingsStorage.getBoard())
        AlertDialog.Builder(context, R.style.AlertDialogStyle)
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

    val sdkKey = MutableLiveData("ca-app-pub-3074235676525198~3986251123")
}
