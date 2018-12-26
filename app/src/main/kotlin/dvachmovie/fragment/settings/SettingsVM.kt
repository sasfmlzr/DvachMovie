package dvachmovie.fragment.settings

import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.usecase.SettingsUseCase
import javax.inject.Inject

class SettingsVM @Inject constructor(
        settingsUseCase: SettingsUseCase
) : ViewModel() {

    val prepareLoading = MutableLiveData<Boolean>()

    init {
        prepareLoading.value = settingsUseCase.getLoadingParam()
    }

    val onPrepareLoadingClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
        prepareLoading.value = isChecked
    }

}