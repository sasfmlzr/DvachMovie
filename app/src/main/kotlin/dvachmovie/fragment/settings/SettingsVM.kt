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
                val boardMap = Boards.themeMap

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

    object Boards{
        val boardMap = hashMapOf<String, String>()

        val themeMap = hashMapOf<String, String>() // Тематика

        val creationMap = hashMapOf<String, String>() // Творчество

        val politycsAndNewsMap = hashMapOf<String, String>() // Политика и новости


        init {
            boardMap["b"] = "Бред"
            boardMap["vg"] = "Video Games General"
            boardMap["mov"] = "Фильмы"
            boardMap["po"] = "Политика"
            boardMap["v"] = "Video Games"
            boardMap["soc"] = "Общение"
            boardMap["mu"] = "Музыка"
            boardMap["mus"] = "Музыканты"

            themeMap["au"] = "Автомобили"
            themeMap["bi"] = "Велосипеды"
            themeMap["biz"] = "Бизнес"
            themeMap["c"] = "Комиксы"
            themeMap["cc"] = "Криптовалюты"
            themeMap["em"] = "Другие страны"
            themeMap["fa"] = "Мода и стиль"
            themeMap["fiz"] = "Физкультура"
            themeMap["fl"] = "Ин. языки"
            themeMap["ftb"] = "Футбол"
            themeMap["hi"] = "Ин. языки"
            themeMap["me"] = "Медицина"
            themeMap["mg"] = "Магия"
            themeMap["mlp"] = "Пони"
            themeMap["mo"] = "Мотоциклы"
            themeMap["mov"] = "Фильмы"
            themeMap["mu"] = "Музыка"
            themeMap["ne"] = "Животные"
            themeMap["psy"] = "Психология"
            themeMap["re"] = "Религия"
            themeMap["sci"] = "Наука"
            themeMap["sf"] = "Научная фантастика"

            themeMap["sn"] = "Паранормальное"
            themeMap["sp"] = "Спорт"
            themeMap["spc"] = "Космос"
            themeMap["tv"] = "Сериалы"
            themeMap["un"] = "Образование"
            themeMap["w"] = "Оружие"
            themeMap["wh"] = "Warhammer"
            themeMap["wm"] = "Военная техника"
            themeMap["wp"] = "Обои и хайрез"
            themeMap["zog"] = "Теории заговора"

            creationMap["de"] = "Дизайн"
            creationMap["di"] = "Столовая"
            creationMap["diy"] = "Хобби"
            creationMap["mus"] = "Музыканты"
            creationMap["pa"] = "Живопись"
            creationMap["p"] = "Фотография"
            creationMap["wrk"] = "Работа"
        }


    }
}
