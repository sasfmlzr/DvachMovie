package dvachmovie.usecase.settingsStorage

import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class GetIsReportBtnVisibleUseCase @Inject constructor(
        private val settingsStorage: SettingsStorage) : UseCase<Unit, Boolean>() {

    override suspend fun execute(input: Unit): Boolean =
            settingsStorage.isReportBtnVisible().await()
}
