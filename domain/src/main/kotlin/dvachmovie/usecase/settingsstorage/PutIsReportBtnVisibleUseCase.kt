package dvachmovie.usecase.settingsStorage

import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class PutIsReportBtnVisibleUseCase @Inject constructor(
        private val settingsStorage: SettingsStorage) : UseCase<Boolean, Unit>() {

    override suspend fun executeAsync(input: Boolean) =
            settingsStorage.putReportBtnVisible(input).await()
}
