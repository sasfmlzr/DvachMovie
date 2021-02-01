package dvachmovie.usecase.settingsstorage

import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class PutCurrentBaseUrlUseCase @Inject constructor(
        private val settingsStorage: SettingsStorage) : UseCase<String, Unit>() {

    override suspend fun executeAsync(input: String) {
        return settingsStorage.putCurrentBaseUrl(input).await()
    }
}
