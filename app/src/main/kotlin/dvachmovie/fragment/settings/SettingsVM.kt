package dvachmovie.fragment.settings

import android.content.Context
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvachmovie.BuildConfig
import dvachmovie.R
import dvachmovie.api.Boards
import dvachmovie.architecture.ScopeProvider
import dvachmovie.architecture.logging.Logger
import dvachmovie.pipe.settingsstorage.GetBoardPipe
import dvachmovie.pipe.settingsstorage.GetIsAllowGesturePipe
import dvachmovie.pipe.settingsstorage.GetIsListBtnVisiblePipe
import dvachmovie.pipe.settingsstorage.GetIsReportBtnVisiblePipe
import dvachmovie.pipe.settingsstorage.PutBoardPipe
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsVM @Inject constructor(
        private val scopeProvider: ScopeProvider,
        private val putBoardPipe: PutBoardPipe,
        logger: Logger,
        private val getIsReportBtnVisiblePipe: GetIsReportBtnVisiblePipe,
        private val getIsListBtnVisiblePipe: GetIsListBtnVisiblePipe,
        private val getIsAllowGesturePipe: GetIsAllowGesturePipe,
        private val getBoardPipe: GetBoardPipe
) : ViewModel() {

    companion object {
        private const val TAG = "SettingsVM"
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    var board = ""
    val isReportBtnVisible = MutableLiveData<Boolean>()
    val isListBtnVisible = MutableLiveData<Boolean>()
    val isGestureEnabled = MutableLiveData<Boolean>()

    /** onPrepareLoadingClicked unused in the future*/
    init {
        addField()
    }

    fun addField() {
        board = getBoardPipe.execute(Unit)

        isReportBtnVisible.value = getIsReportBtnVisiblePipe.execute(Unit)
        isListBtnVisible.value = getIsListBtnVisiblePipe.execute(Unit)
        isGestureEnabled.value = getIsAllowGesturePipe.execute(Unit)
    }

    val onCleanDB = MutableLiveData<Boolean>(false)

    val onChangeBoard = MutableLiveData<Boolean>(false)

    val version = MutableLiveData<String>(BuildConfig.VERSION_NAME)

    val onReportSwitchClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
                isReportBtnVisible.value = isChecked
            }

    val onListSwitchClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
                isListBtnVisible.value = isChecked
            }

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

    private fun showChangeBoardDialog(context: Context, boardMap: HashMap<String, String>) {
        var checkedItem = boardMap.keys.indexOf(board)
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
                        scopeProvider.ioScope.launch(Job()) {
                            putBoardPipe.execute(boardMap.keys.elementAt(checkedItem))

                            onChangeBoard.postValue(true)
                        }
                    }
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .show()
    }
}
