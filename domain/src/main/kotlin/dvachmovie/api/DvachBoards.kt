package dvachmovie.api

object DvachBoards {

    fun isBoardExists(board: String): Boolean =
            defaultMap.containsKey(board) ||
                    popularMap.containsKey(board) ||
                    themeMap.containsKey(board) ||
                    creationMap.containsKey(board) ||
                    politicsAndNewsMap.containsKey(board) ||
                    techniqueAndSoftwareMap.containsKey(board) ||
                    gamesMap.containsKey(board) ||
                    japanCultureMap.containsKey(board) ||
                    adultOtherMap.containsKey(board) ||
                    adultMap.containsKey(board)

    val defaultMap = linkedMapOf<String, String>()

    val popularMap = hashMapOf<String, String>()

    val themeMap = hashMapOf<String, String>() // Тематика

    val creationMap = hashMapOf<String, String>() // Творчество

    val politicsAndNewsMap = hashMapOf<String, String>() // Политика и новости

    val techniqueAndSoftwareMap = hashMapOf<String, String>() // Техника и софт

    val gamesMap = hashMapOf<String, String>() // Игры

    val japanCultureMap = hashMapOf<String, String>() // Японская культура

    val adultOtherMap = hashMapOf<String, String>() // Разное 18+

    val adultMap = hashMapOf<String, String>() // Взрослым 18+

    init {
        defaultMap["b"] = "/b/ - Бред"

        popularMap["b"] = "/b/ - Бред"
        popularMap["vg"] = "/vg/ - Video Games General"
        popularMap["mov"] = "/mov/ - Фильмы"
        popularMap["po"] = "/po/ - Политика"
        popularMap["v"] = "/v/ - Video Games"
        popularMap["mu"] = "/mu/ - Музыка"
        popularMap["mus"] = "/mus/ - Музыканты"

        themeMap["au"] = "/au/ - Автомобили"
        themeMap["bi"] = "/bi/ - Велосипеды"
        themeMap["biz"] = "/biz/ - Бизнес"
        themeMap["bo"] = "/bo/ - Книги"
        themeMap["c"] = "/c/ - Комиксы"
        themeMap["cc"] = "/cc/ - Криптовалюты"
        themeMap["em"] = "/em/ - Другие страны"
        themeMap["fa"] = "/fa/ - Мода и стиль"
        themeMap["fiz"] = "/fiz/ - Физкультура"
        themeMap["fl"] = "/fl/ - Ин. языки"
        themeMap["ftb"] = "/ftb/ - Футбол"
        themeMap["hh"] = "/hh/ - Hip-hop"
        themeMap["hi"] = "/hi/ - История"
        themeMap["me"] = "/me/ - Медицина"
        themeMap["mg"] = "/mg/ - Магия"
        themeMap["mlp"] = "/mlp/ - Пони"
        themeMap["mo"] = "/mo/ - Мотоциклы"
        themeMap["mov"] = "/mov/ - Фильмы"
        themeMap["mu"] = "/mu/ - Музыка"
        themeMap["ne"] = "/ne/ - Животные"
        themeMap["psy"] = "/psy/ - Психология"
        themeMap["re"] = "/re/ - Религия"
        themeMap["sci"] = "/sci/ - Наука"
        themeMap["sf"] = "/sf/ - Научная фантастика"

        themeMap["sn"] = "/sn/ - Паранормальное"
        themeMap["sp"] = "/sp/ - Спорт"
        themeMap["spc"] = "/spc/ - Космос"
        themeMap["tv"] = "/tv/ - Тв и кино"
        themeMap["un"] = "/un/ - Образование"
        themeMap["w"] = "/w/ - Оружие"
        themeMap["wh"] = "/wh/ - Warhammer"
        themeMap["wm"] = "/wm/ - Военная техника"
        themeMap["wp"] = "/wp/ - Обои и хайрез"
        themeMap["zog"] = "/zog/ - Теории заговора"
        themeMap["kpop"] = "/kpop/ - K-pop"

        creationMap["de"] = "/de/ - Дизайн"
        creationMap["di"] = "/di/ - Столовая"
        creationMap["diy"] = "/diy/ - Хобби"
        creationMap["mus"] = "/mus/ - Музыканты"
        creationMap["pa"] = "/pa/ - Живопись"
        creationMap["p"] = "/p/ - Фотография"
        creationMap["wrk"] = "/wrk/ - Работа"
        creationMap["trv"] = "/trv/ - Путешествия и отдых"

        politicsAndNewsMap["po"] = "/po/ - Политика"
        politicsAndNewsMap["news"] = "/news/ - Новости"
        politicsAndNewsMap["int"] = "/int/ - International"
        politicsAndNewsMap["hry"] = "/hry/ - Х Р Ю"

        techniqueAndSoftwareMap["gd"] = "/gd/ - Gamedev"
        techniqueAndSoftwareMap["hw"] = "/hw/ - Комп. железо"
        techniqueAndSoftwareMap["mobi"] = "/mobi/ - Моб. Устройства"
        techniqueAndSoftwareMap["pr"] = "/pr/ - Программирование"
        techniqueAndSoftwareMap["ra"] = "/ra/ - Радиотехника"
        techniqueAndSoftwareMap["s"] = "/s/ - Программы"
        techniqueAndSoftwareMap["t"] = "/t/ - Техника"
        techniqueAndSoftwareMap["web"] = "/web/ - Веб-мастера"

        gamesMap["bg"] = "/bg/ - Настольные игры"
        gamesMap["cg"] = "/cg/ - Консоли"
        gamesMap["gsg"] = "/gsg/ - Grand Strategy Games"
        gamesMap["ruvn"] = "/ruvn/ - Российские виз.новеллы"
        gamesMap["tes"] = "/tes/ - The Elder Scrolls"
        gamesMap["v"] = "/v/ - Video Games"
        gamesMap["vg"] = "/vg/ - Video Games General"
        gamesMap["wr"] = "/wr/ - Текстовые РПГ"

        japanCultureMap["a"] = "/a/ - Аниме"
        japanCultureMap["fd"] = "/fd/ - Фэндом"
        japanCultureMap["ja"] = "/ja/ - Японская культура"
        japanCultureMap["ma"] = "/ma/ - Манга"
        japanCultureMap["vn"] = "/vn/ - Визуальные новеллы"

        adultOtherMap["d"] = "/d/ - Дискуссии о Два.ч"
        adultOtherMap["b"] = "/b/ - Бред"
        adultOtherMap["soc"] = "/soc/ - Общение"
        adultOtherMap["media"] = "/media/ - Анимация"
        adultOtherMap["r"] = "/r/ - Реквесты"
        adultOtherMap["api"] = "/api/ - API"
        adultOtherMap["rf"] = "/rf/ - Убежище"
        adultOtherMap["o"] = "/o/ - Рисовач"

        adultMap["fg"] = "/fg/ - Трапы"
        adultMap["fur"] = "/fur/ - Фурри"
        adultMap["gg"] = "/gg/ - Красивые девушки"
        adultMap["ga"] = "/ga/ - Геи"
        adultMap["vape"] = "/vape/ - Электронные сигареты"
        adultMap["h"] = "/h/ - Хентай"
        adultMap["ho"] = "/ho/ - Прочий хентай"
        adultMap["hc"] = "/hc/ - Hardcore"
        adultMap["e"] = "/e/ - Extreme pron"
        adultMap["fet"] = "/fet/ - Фетиш"
        adultMap["sex"] = "/sex/ - Секс и отношения"
        adultMap["fag"] = "/fag/ - Фагготрия"
    }
}
