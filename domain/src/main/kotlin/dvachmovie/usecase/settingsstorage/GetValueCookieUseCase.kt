package dvachmovie.usecase.settingsStorage

import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetValueCookieUseCase @Inject constructor(
        private val settingsStorage: SettingsStorage) : UseCase<Unit, String>() {

    override suspend fun executeAsync(input: Unit): String =
            settingsStorage.getCookieAsync().await()

    override fun execute(input: Unit): String =
            settingsStorage.getCookie()
}
