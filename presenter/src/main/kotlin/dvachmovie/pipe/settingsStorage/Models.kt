package dvachmovie.pipe.settingsStorage

import dvachmovie.PresenterModel

data class CookieStringModel(val cookie: String) : PresenterModel

data class BoardModel(val board: String) : PresenterModel

data class IsAllowGestureModel(val value: Boolean) : PresenterModel

data class IsListBtnVisibleModel(val value: Boolean) : PresenterModel

data class IsLoadingEveryTimeModel(val value: Boolean) : PresenterModel

data class IsReportBtnVisibleModel(val value: Boolean) : PresenterModel
