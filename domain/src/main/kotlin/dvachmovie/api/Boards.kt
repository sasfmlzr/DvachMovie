package dvachmovie.api

object Boards {
    val defaultMap = linkedMapOf<String, String>()

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
        defaultMap["kpop"] = "K-pop"

        popularMap["b"] = "Бред"
        popularMap["vg"] = "Video Games General"
        popularMap["mov"] = "Фильмы"
        popularMap["po"] = "Политика"
        popularMap["v"] = "Video Games"
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

        themeMap["kpop"] = "K-pop"

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
        japanCultureMap["fd"] = "Фэндом"
        japanCultureMap["ja"] = "Японская культура"
        japanCultureMap["ma"] = "Манга"
        japanCultureMap["vn"] = "Визуальные новеллы"

        adultOtherMap["d"] = "Дискуссии о Два.ч"
        adultOtherMap["b"] = "Бред"
        adultOtherMap["soc"] = "Общение"
        adultOtherMap["media"] = "Анимация"
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
