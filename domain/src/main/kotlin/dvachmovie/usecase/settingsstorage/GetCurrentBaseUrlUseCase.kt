package dvachmovie.usecase.settingsstorage

import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetCurrentBaseUrlUseCase @Inject constructor(
        private val settingsStorage: SettingsStorage) : UseCase<Unit, String>() {

    override suspend fun executeAsync(input: Unit): String =
            settingsStorage.getCurrentBaseUrlAsync().await()

    override fun execute(input: Unit): String =
            settingsStorage.getCurrentBaseUrl()
}
