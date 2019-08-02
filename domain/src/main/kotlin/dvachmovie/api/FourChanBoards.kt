package dvachmovie.api

object FourChanBoards {

    fun isBoardExists(board: String): Boolean =
            defaultMap.containsKey(board) ||
                    japanCultureMap.containsKey(board) ||
                    videoGamesMap.containsKey(board) ||
                    interestsMap.containsKey(board) ||
                    creativeMap.containsKey(board) ||
                    otherMap.containsKey(board) ||
                    miscMap.containsKey(board) ||
                    japanCultureMap.containsKey(board) ||
                    adultMap.containsKey(board)

    val defaultMap = linkedMapOf<String, String>()

    val japanCultureMap = hashMapOf<String, String>() // Japan culture

    val videoGamesMap = hashMapOf<String, String>() // Video games

    val interestsMap = hashMapOf<String, String>() // Interests

    val creativeMap = hashMapOf<String, String>() // Creative

    val otherMap = hashMapOf<String, String>() // Other

    val miscMap = hashMapOf<String, String>() // Misc NSFW 18+

    val adultMap = hashMapOf<String, String>() // Adult NSFW 18+

    init {
        defaultMap["b"] = "/b/ - Random"

        japanCultureMap["a"] = "/a/ - Anime & Manga"
        japanCultureMap["c"] = "/c/ - Anime/Cute"
        japanCultureMap["w"] = "/w/ - Anime/Wallpapers"
        japanCultureMap["m"] = "/m/ - Mecha"
        japanCultureMap["cgl"] = "/cgl/ - Cosplay & EGL"
        japanCultureMap["cm"] = "/cm/ - Cute/Male"
        japanCultureMap["f"] = "/f/ - Flash"
        japanCultureMap["n"] = "/n/ - Transportation"
        japanCultureMap["jp"] = "/jp/ - Otaku Culture"

        videoGamesMap["v"] = "/v/ - Video Games"
        videoGamesMap["vg"] = "/vg/ - Video Game Generals"
        videoGamesMap["vp"] = "/vp/ - Pokémon"
        videoGamesMap["vr"] = "/vr/ - Retro Games"

        interestsMap["co"] = "/co/ - Comics & Cartoons"
        interestsMap["g"] = "/g/ - Technology"
        interestsMap["tv"] = "/tv/ - Television & Film"
        interestsMap["k"] = "/k/ - Weapons"
        interestsMap["o"] = "/o/ - Auto"
        interestsMap["an"] = "/an/ - Animals & Nature"
        interestsMap["tg"] = "/tg/ - Traditional Games"
        interestsMap["sp"] = "/sp/ - Sports"
        interestsMap["asp"] = "/asp/ - Alternative Sports"
        interestsMap["sci"] = "/sci/ - Science & Maths"
        interestsMap["his"] = "/his/ - History & Humanities"
        interestsMap["int"] = "/int/ - International"
        interestsMap["out"] = "/out/ - Outdoors"
        interestsMap["toy"] = "/toy/ - Toys"

        creativeMap["i"] = "/i/ - Oekaki"
        creativeMap["po"] = "/po/ - Papercraft & Origami"
        creativeMap["p"] = "/p/ - Photography"
        creativeMap["ck"] = "/ck/ - Food & Cooking"
        creativeMap["ic"] = "/ic/ - Artwork/Critique"
        creativeMap["wg"] = "/wg/ - Wallpapers/General"
        creativeMap["lit"] = "/lit/ - Literature"
        creativeMap["mu"] = "/mu/ - Music"
        creativeMap["fa"] = "/fa/ - Fashion"
        creativeMap["3"] = "/3/ - 3DCG"
        creativeMap["gd"] = "/gd/ - Graphic Design"
        creativeMap["diy"] = "/diy/ - Do-It-Yourself"
        creativeMap["wsg"] = "/wsg/ - Worksafe GIF"
        creativeMap["qst"] = "/qst/ - Quests"

        otherMap["biz"] = "/biz/ - Business & Finance"
        otherMap["trv"] = "/trv/ - Travel"
        otherMap["fit"] = "/fit/ - Fitness"
        otherMap["x"] = "/x/ - Paranormal"
        otherMap["adv"] = "/adv/ - Advice"
        otherMap["lgbt"] = "/lgbt/ - LGBT"
        otherMap["mlp"] = "/mlp/ - Pony"
        otherMap["news"] = "/news/ - Current News"
        otherMap["wsr"] = "/wsr/ - Worksafe Requests"
        otherMap["vip"] = "/vip/ - Very Important Posts"

        miscMap["b"] = "/b/ - Random"
        miscMap["r9k"] = "/r9k/ - ROBOT9001"
        miscMap["pol"] = "/pol/ - Politically Incorrect"
        miscMap["bant"] = "/bant/ - International/Random"
        miscMap["soc"] = "/soc/ - Cams & Meetups"
        miscMap["s4s"] = "/s4s/ - Shit 4chan Says"

        adultMap["s"] = "/s/ - Sexy Beautiful Women"
        adultMap["hc"] = "/hc/ - Hardcore"
        adultMap["hm"] = "/hm/ - Handsome Men"
        adultMap["h"] = "/h/ - Hentai"
        adultMap["e"] = "/e/ - Ecchi"
        adultMap["u"] = "/u/ - Yuri"
        adultMap["d"] = "/d/ - Hentai/Alternative"
        adultMap["y"] = "/y/ - Yaoi"
        adultMap["t"] = "/t/ - Torrents"
        adultMap["hr"] = "/hr/ - High Resolution"
        adultMap["gif"] = "/gif/ - Adult GIF"
        adultMap["aco"] = "/aco/ - Adult Cartoons"
        adultMap["r"] = "/r/ - Adult Requests"
    }
}
