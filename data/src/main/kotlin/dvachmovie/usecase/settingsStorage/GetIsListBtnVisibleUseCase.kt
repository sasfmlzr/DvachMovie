package dvachmovie.usecase.settingsStorage

import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class GetIsListBtnVisibleUseCase @Inject constructor(
        private val settingsStorage: SettingsStorage) : UseCase<Unit, Boolean>() {

    override suspend fun execute(input: Unit): Boolean =
            settingsStorage.isListBtnVisible().await()
}
