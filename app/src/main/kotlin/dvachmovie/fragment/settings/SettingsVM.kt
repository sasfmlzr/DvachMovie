package dvachmovie.fragment.settings

import android.content.Context
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.architecture.logging.Logger
import dvachmovie.storage.SettingsStorage
import javax.inject.Inject

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

    private fun showChangeBoardDialog(context: Context, boardMap: HashMap<String, String>) {
        var checkedItem = boardMap.keys.indexOf(settingsStorage.getBoard())
        AlertDialog.Builder(context)
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

    object Boards {
        val popularMap = hashMapOf<String, String>()

        val themeMap = hashMapOf<String, String>() // Тематика

        val creationMap = hashMapOf<String, String>() // Творчество

        val politycsAndNewsMap = hashMapOf<String, String>() // Политика и новости

        val techniqueAndSoftwareMap = hashMapOf<String, String>() // Техника и софт

        val gamesMap = hashMapOf<String, String>() // Игры

        val japanCultureMap = hashMapOf<String, String>() // Японская культура

        val adultOtherMap = hashMapOf<String, String>() // Разное 18+

        val adultMap = hashMapOf<String, String>() // Взрослым 18+

        init {
            popularMap["vg"] = "Video Games General"
            popularMap["mov"] = "Фильмы"
            popularMap["po"] = "Политика"
            popularMap["v"] = "Video Games"
            popularMap["soc"] = "Общение"
            popularMap["mu"] = "Музыка"
            popularMap["mus"] = "Музыканты"

            themeMap["au"] = "Автомобили"
            themeMap["bi"] = "Велосипеды"
            themeMap["biz"] = "Бизнес"
            themeMap["bo"] = "Книги"
            themeMap["c"] = "Комиксы"
            themeMap["cc"] = "Криптовалюты"
            themeMap["em"] = "Другие страны"
            themeMap["fa"] = "Мода и стиль"
            themeMap["fiz"] = "Физкультура"
            themeMap["fl"] = "Ин. языки"
            themeMap["ftb"] = "Футбол"
            themeMap["hh"] = "Hip-hop"
            themeMap["hi"] = "История"
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
            themeMap["tv"] = "Тв и кино"
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
            creationMap["trv"] = "Работа"

            politycsAndNewsMap["po"] = "Политика"
            politycsAndNewsMap["news"] = "Новости"
            politycsAndNewsMap["int"] = "International"
            politycsAndNewsMap["hry"] = "Х Р Ю"

            techniqueAndSoftwareMap["gd"] = "Gamedev"
            techniqueAndSoftwareMap["hw"] = "Комп. железо"
            techniqueAndSoftwareMap["mobi"] = "Моб. Устройства"
            techniqueAndSoftwareMap["pr"] = "Программирование"
            techniqueAndSoftwareMap["ra"] = "Радиотехника"
            techniqueAndSoftwareMap["s"] = "Программы"
            techniqueAndSoftwareMap["t"] = "Техника"
            techniqueAndSoftwareMap["web"] = "Веб-мастера"

            gamesMap["bg"] = "Настольные игры"
            gamesMap["cg"] = "Консоли"
            gamesMap["gsg"] = "Grand Strategy Games"
            gamesMap["ruvn"] = "Российские виз.новеллы"
            gamesMap["tes"] = "The Elder Scrolls"
            gamesMap["v"] = "Video Games"
            gamesMap["vg"] = "Video Games General"
            gamesMap["wr"] = "Текстовые РПГ"

            japanCultureMap["a"] = "Аниме"
            japanCultureMap["fd"] = "Фэедом"
            japanCultureMap["ja"] = "Японская культура"
            japanCultureMap["ma"] = "Манга"
            japanCultureMap["vn"] = "Визуальные новеллы"

            adultOtherMap["d"] = "Дискуссии о Два.ч"
            adultOtherMap["b"] = "Бред"
            adultOtherMap["soc"] = "Общение"
            adultOtherMap["meida"] = "Анимация"
            adultOtherMap["r"] = "Реквесты"
            adultOtherMap["api"] = "API"
            adultOtherMap["rf"] = "Убежище"
            adultOtherMap["o"] = "Рисовач"

            adultMap["fg"] = "Трапы"
            adultMap["fur"] = "Фурри"
            adultMap["gg"] = "Красивые девушки"
            adultMap["ga"] = "Геи"
            adultMap["vape"] = "Электронные сигареты"
            adultMap["h"] = "Хентай"
            adultMap["ho"] = "Прочий хентай"
            adultMap["hc"] = "Hardcore"
            adultMap["e"] = "Extreme pron"
            adultMap["fet"] = "Фетиш"
            adultMap["sex"] = "Секс и отношения"
            adultMap["fag"] = "Фагготрия"
        }

    }
}
