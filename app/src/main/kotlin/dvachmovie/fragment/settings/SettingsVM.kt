package dvachmovie.fragment.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvachmovie.BuildConfig
import dvachmovie.R
import dvachmovie.api.Boards
import dvachmovie.architecture.ScopeProvider
import dvachmovie.architecture.logging.Logger
import dvachmovie.pipe.settingsStorage.GetBoardPipe
import dvachmovie.pipe.settingsStorage.GetIsAllowGesturePipe
import dvachmovie.pipe.settingsStorage.GetIsListBtnVisiblePipe
import dvachmovie.pipe.settingsStorage.GetIsLoadingEveryTimePipe
import dvachmovie.pipe.settingsStorage.GetIsReportBtnVisiblePipe
import dvachmovie.pipe.settingsStorage.PutBoardPipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsVM @Inject constructor(
        private val scopeProvider: ScopeProvider,
        private val putBoardPipe: PutBoardPipe,
        private val getIsLoadingEveryTimePipe: GetIsLoadingEveryTimePipe,
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

    val prepareLoading = MutableLiveData<Boolean>()

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
        prepareLoading.value = getIsLoadingEveryTimePipe.execute(Unit)

        isReportBtnVisible.value = getIsReportBtnVisiblePipe.execute(Unit)
        isListBtnVisible.value = getIsListBtnVisiblePipe.execute(Unit)
        isGestureEnabled.value = getIsAllowGesturePipe.execute(Unit)

    }

    val onPrepareLoadingClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
                prepareLoading.value = isChecked
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

    val onGetProVersion =
            View.OnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("market://details?id=com.dvachmovie.android.pro")
                startActivity(it.context, intent, null)
            }

    private fun showChangeBoardDialog(context: Context, boardMap: HashMap<String, String>) {
        scopeProvider.uiScope.launch {
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
                            scopeProvider.uiScope.launch {
                                withContext(Dispatchers.Default) {
                                    putBoardPipe.execute(boardMap.keys.elementAt(checkedItem))
                                }
                                onChangeBoard.value = true
                            }
                        }
                    }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .show()
        }
    }

    val sdkKey = MutableLiveData("ca-app-pub-3074235676525198~1117408577")
}
