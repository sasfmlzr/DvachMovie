package dvachmovie.usecase.settingsstorage

import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetIsReportBtnVisibleUseCase @Inject constructor(
        private val settingsStorage: SettingsStorage) : UseCase<Unit, Boolean>() {

    override suspend fun executeAsync(input: Unit): Boolean =
            settingsStorage.isReportBtnVisibleAsync().await()

    override fun execute(input: Unit): Boolean =
            settingsStorage.isReportBtnVisible()
}
