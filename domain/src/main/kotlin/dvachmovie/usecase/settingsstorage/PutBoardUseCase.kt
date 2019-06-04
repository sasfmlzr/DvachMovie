package dvachmovie.usecase.settingsStorage

import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class PutBoardUseCase @Inject constructor(
        private val settingsStorage: SettingsStorage) : UseCase<String, Unit>() {

    override suspend fun executeAsync(input: String) =
            settingsStorage.putBoard(input).await()
}
