package dvachmovie.fragment.settings

import android.content.DialogInterface
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.repository.db.MovieDBRepository
import dvachmovie.usecase.SettingsUseCase
import javax.inject.Inject

class SettingsVM @Inject constructor(
        private val movieRepository: MovieDBRepository,
        settingsUseCase: SettingsUseCase
) : ViewModel() {

    val prepareLoading = MutableLiveData<Boolean>()

    val onRefreshDB = MutableLiveData<Boolean>()

    init {
        prepareLoading.value = settingsUseCase.getLoadingParam()
        onRefreshDB.value = false
    }

    val onPrepareLoadingClicked =
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
        prepareLoading.value = isChecked
    }

    val onRefreshDatabase =
            View.OnClickListener {
                AlertDialog.Builder(it.context)
                        .setTitle("Confirmation")
                        .setMessage("Database will clean")
                        .setPositiveButton("Ok") { _, _ ->
                            println("refresh")
                            onRefreshDB.value = true
                        }
                        .setNegativeButton("Cancel") {_,_ ->}
                        .show()
            }

}