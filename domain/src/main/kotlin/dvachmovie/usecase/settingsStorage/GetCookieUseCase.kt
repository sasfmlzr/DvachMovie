package dvachmovie.usecase.settingsStorage

import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class GetCookieUseCase @Inject constructor(
        private val settingsStorage: SettingsStorage) : UseCase<Unit, String>() {

    override suspend fun execute(input: Unit): String =
            settingsStorage.getCookie().await()
}
