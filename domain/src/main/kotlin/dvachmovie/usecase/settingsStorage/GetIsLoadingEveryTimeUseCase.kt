package dvachmovie.usecase.settingsStorage

import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class GetIsLoadingEveryTimeUseCase @Inject constructor(
        private val settingsStorage: SettingsStorage) : UseCase<Unit, Boolean>() {

    override suspend fun executeAsync(input: Unit): Boolean =
            settingsStorage.isLoadingEveryTimeAsync().await()

    override fun execute(input: Unit): Boolean =
            settingsStorage.isLoadingEveryTime()
}
