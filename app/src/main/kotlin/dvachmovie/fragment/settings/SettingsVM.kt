package dvachmovie.fragment.settings

import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent.KEYCODE_DPAD_RIGHT
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvachmovie.AppConfig
import dvachmovie.BuildConfig
import dvachmovie.R
import dvachmovie.api.DvachBoards
import dvachmovie.api.FourChanBoards
import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.moviestorage.EraseMovieStoragePipe
import dvachmovie.pipe.settingsstorage.GetBoardPipe
import dvachmovie.pipe.settingsstorage.GetCurrentBaseUrlPipe
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
import kotlinx.coroutines.withContext
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
        private val eraseMovieStoragePipe: EraseMovieStoragePipe,
        private val getCurrentBaseUrlPipe: GetCurrentBaseUrlPipe
) : ViewModel() {

    private lateinit var board: String
    val isReportBtnVisible = MutableLiveData<Boolean>()
    val isReportBtnShow = MutableLiveData<Boolean>()
    val isListBtnShow= MutableLiveData<Boolean>()
    val isGestureEnabled = MutableLiveData<Boolean>()

    val isDvachBoardsVisible = MutableLiveData<Boolean>()
    val isFourChanBoardsVisible = MutableLiveData<Boolean>()

    init {
        addFields()
    }

    fun addFields() {
        board = getBoardPipe.execute(Unit)

        isReportBtnShow.value = getIsReportBtnVisiblePipe.execute(Unit)
        isListBtnShow.value = getIsListBtnVisiblePipe.execute(Unit)
        isGestureEnabled.value = getIsAllowGesturePipe.execute(Unit)
    }

    fun setReportBtnVisible(){
        when (getCurrentBaseUrl()) {
            AppConfig.DVACH_URL -> isReportBtnVisible.value = true
        }
    }

    fun getCurrentBaseUrl() = getCurrentBaseUrlPipe.execute(Unit)

    val onReportSwitchClicked = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        isReportBtnShow.value = isChecked
        viewModelScope.launch {
            putReportBtnVisiblePipe.execute(isChecked)
        }
    }

    val onListSwitchClicked = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        isListBtnShow.value = isChecked
        viewModelScope.launch {
            putIsListBtnVisiblePipe.execute(isChecked)
        }
    }

    val onGestureLoadingClicked = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        isGestureEnabled.value = isChecked
        viewModelScope.launch {
            putIsAllowGesturePipe.execute(isChecked)
        }
    }

    val version = MutableLiveData(BuildConfig.VERSION_NAME)

    val onCleanDatabase = View.OnClickListener {
        AlertDialog.Builder(it.context, R.style.AlertDialogStyle)
                .setTitle("Confirmation")
                .setMessage("Database will clean")
                .setPositiveButton("Ok") { _, _ ->
                    recreateMoviesDB()
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .show()
    }

    val onRefreshMovies = View.OnClickListener {
        AlertDialog.Builder(it.context, R.style.AlertDialogStyle)
                .setTitle("Confirmation")
                .setMessage("Movies will refresh")
                .setPositiveButton("Ok") { _, _ ->
                    reInitMovies(true)
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .show()
    }

    lateinit var recreateMoviesDB: () -> Unit
    lateinit var routeToStartFragment: (isRefresh: Boolean) -> Unit

    fun reInitMovies(isRefresh: Boolean) {
        eraseMovieStoragePipe.execute(Unit)
        routeToStartFragment(isRefresh)
    }

    fun createClickListenerForSetBoards(boardMap: HashMap<String, String>) =
            View.OnClickListener {
                showChangeBoardDialog(it.context, boardMap)
            }

    //------------2ch.hk boards started------------//
    val onSetDvachPopularBoard = createClickListenerForSetBoards(DvachBoards.popularMap)

    val onSetDvachThemeBoard = createClickListenerForSetBoards(DvachBoards.themeMap)

    val onSetDvachCreationBoard = createClickListenerForSetBoards(DvachBoards.creationMap)

    val onSetDvachPolNewsBoard = createClickListenerForSetBoards(DvachBoards.politicsAndNewsMap)

    val onSetDvachTechSoftBoard = createClickListenerForSetBoards(DvachBoards.techniqueAndSoftwareMap)

    val onSetDvachGamesBoard = createClickListenerForSetBoards(DvachBoards.gamesMap)

    val onSetDvachJapanBoard = createClickListenerForSetBoards(DvachBoards.japanCultureMap)

    val onSetDvachAdultBoard = createClickListenerForSetBoards(DvachBoards.adultMap)

    val onSetDvachAdultOtherBoard = createClickListenerForSetBoards(DvachBoards.adultOtherMap)
    //------------2ch.hk boards finished------------//

    //------------4chan.org boards started------------//
    val onSetFourChanJapanCultureBoard = createClickListenerForSetBoards(FourChanBoards.japanCultureMap)

    val onSetFourChanVideoGamesBoard = createClickListenerForSetBoards(FourChanBoards.videoGamesMap)

    val onSetFourChanInterestsBoard = createClickListenerForSetBoards(FourChanBoards.interestsMap)

    val onSetFourChanCreativeBoard = createClickListenerForSetBoards(FourChanBoards.creativeMap)

    val onSetFourChanOtherBoard = createClickListenerForSetBoards(FourChanBoards.otherMap)

    val onSetFourChanMiscBoard = createClickListenerForSetBoards(FourChanBoards.miscMap)

    val onSetFourChanAdultBoard = createClickListenerForSetBoards(FourChanBoards.adultMap)
    //------------4chan.org boards finished------------//

    private fun showChangeBoardDialog(context: Context, boardMap: HashMap<String, String>) {
        var checkedItem = boardMap.keys.indexOf(board)

        AlertDialog.Builder(context, R.style.AlertDialogStyle)
                .setTitle("Set board")
                .setSingleChoiceItems(
                        boardMap.values.toTypedArray(),
                        checkedItem
                ) { dialog, which ->
                    checkedItem = which
                    (dialog as AlertDialog).getButton(DialogInterface.BUTTON_POSITIVE).isPressed = true
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).requestFocus()
                }
                .setOnKeyListener { dialog, keyCode, _ ->
                    val btnNegative = (dialog as AlertDialog).getButton(DialogInterface.BUTTON_NEGATIVE)
                    val btnPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    if (keyCode == KEYCODE_DPAD_RIGHT && !btnNegative.isFocused && !btnPositive.isFocused) {
                        btnNegative.isPressed = true
                        btnNegative.requestFocus()
                        true
                    } else {
                        false
                    }
                }
                .setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        view?.isPressed = true
                    }
                })
                .setPositiveButton("Ok") { _, _ ->
                    if (checkedItem != -1) {
                        viewModelScope.launch {
                            withContext(scopeProvider.ioScope.coroutineContext + Job()) {
                                putBoardPipe.execute(boardMap.keys.elementAt(checkedItem))
                            }
                            reInitMovies(false)
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
