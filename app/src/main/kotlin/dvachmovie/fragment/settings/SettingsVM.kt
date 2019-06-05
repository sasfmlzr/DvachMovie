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
import dvachmovie.pipe.android.EraseMovieStoragePipe
import dvachmovie.pipe.settingsstorage.GetBoardPipe
import dvachmovie.pipe.settingsstorage.GetIsAllowGesturePipe
import dvachmovie.pipe.settingsstorage.GetIsListBtnVisiblePipe
import dvachmovie.pipe.settingsstorage.GetIsReportBtnVisiblePipe
import dvachmovie.pipe.settingsstorage.PutBoardPipe
import dvachmovie.pipe.settingsstorage.PutIsAllowGesturePipe
import dvachmovie.pipe.settingsstorage.PutIsListBtnVisiblePipe
import dvachmovie.pipe.settingsstorage.PutIsReportBtnVisiblePipe
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsVM @Inject constructor(
        private val scopeProvider: ScopeProvider,
        private val putBoardPipe: PutBoardPipe,
        private val getIsReportBtnVisiblePipe: GetIsReportBtnVisiblePipe,
        private val getIsListBtnVisiblePipe: GetIsListBtnVisiblePipe,
        private val getIsAllowGesturePipe: GetIsAllowGesturePipe,
        private val getBoardPipe: GetBoardPipe,
        private val putReportBtnVisiblePipe: PutIsReportBtnVisiblePipe,
        private val putIsListBtnVisiblePipe: PutIsListBtnVisiblePipe,
        private val putIsAllowGesturePipe: PutIsAllowGesturePipe,
        private val eraseMovieStoragePipe: EraseMovieStoragePipe
) : ViewModel() {

    private lateinit var board: String
    val isReportBtnVisible = MutableLiveData<Boolean>()
    val isListBtnVisible = MutableLiveData<Boolean>()
    val isGestureEnabled = MutableLiveData<Boolean>()

    init {
        addFields()
    }

    fun addFields() {
        board = getBoardPipe.execute(Unit)

        isReportBtnVisible.value = getIsReportBtnVisiblePipe.execute(Unit)
        isListBtnVisible.value = getIsListBtnVisiblePipe.execute(Unit)
        isGestureEnabled.value = getIsAllowGesturePipe.execute(Unit)
    }

    val onReportSwitchClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
                isReportBtnVisible.value = isChecked
                viewModelScope.launch {
                    putReportBtnVisiblePipe.execute(isChecked)
                }
            }

    val onListSwitchClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
                isListBtnVisible.value = isChecked
                viewModelScope.launch {
                    putIsListBtnVisiblePipe.execute(isChecked)
                }
            }

    val onGestureLoadingClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
                isGestureEnabled.value = isChecked
                viewModelScope.launch {
                    putIsAllowGesturePipe.execute(isChecked)
                }
            }

    val version = MutableLiveData<String>(BuildConfig.VERSION_NAME)

    val onRefreshDatabase =
            View.OnClickListener {
                AlertDialog.Builder(it.context, R.style.AlertDialogStyle)
                        .setTitle("Confirmation")
                        .setMessage("Database will clean")
                        .setPositiveButton("Ok") { _, _ ->
                            recreateMoviesDB()
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
            }

    lateinit var recreateMoviesDB: () -> Unit
    lateinit var routeToStartFragment: () -> Unit

    fun reInitMovies() {
        eraseMovieStoragePipe.execute(Unit)
        routeToStartFragment()
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
                            reInitMovies()
                        }
                    }
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .show()
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}
