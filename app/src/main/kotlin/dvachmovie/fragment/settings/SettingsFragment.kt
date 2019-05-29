package dvachmovie.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dvachmovie.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentSettingsBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.pipe.settingsStorage.GetBoardPipe
import dvachmovie.pipe.settingsStorage.PutIsAllowGesturePipe
import dvachmovie.pipe.settingsStorage.PutIsListBtnVisiblePipe
import dvachmovie.pipe.settingsStorage.PutIsLoadingEveryTimePipe
import dvachmovie.pipe.settingsStorage.PutIsReportBtnVisiblePipe
import dvachmovie.usecase.EraseMovieStorageUseCase
import dvachmovie.worker.WorkerManager
import kotlinx.android.synthetic.main.include_settings_fragment.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsFragment : BaseFragment<SettingsVM,
        FragmentSettingsBinding>(SettingsVM::class) {

    @Inject
    lateinit var eraseMovieStorageUseCase: EraseMovieStorageUseCase

    @Inject
    lateinit var putLoadingEveryTimePipe: PutIsLoadingEveryTimePipe

    @Inject
    lateinit var putReportBtnVisiblePipe: PutIsReportBtnVisiblePipe

    @Inject
    lateinit var putListBtnVisiblePipe: PutIsListBtnVisiblePipe

    @Inject
    lateinit var putIsAllowGesturePipe: PutIsAllowGesturePipe

    @Inject
    lateinit var getBoardPipe: GetBoardPipe

    override fun getLayoutId() = R.layout.fragment_settings

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState).apply {
            viewModel.addField()
        }
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        configureVM()
        getBoardPipe.execute(Unit)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
    }

    private fun setUpToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.setSupportActionBar(toolbar)
    }

    private fun configureVM() {
        viewModel.prepareLoading.observe(this, Observer {
            putLoadingEveryTimePipe.execute(it)
        })

        viewModel.isReportBtnVisible.observe(this, Observer {
            putReportBtnVisiblePipe.execute(it)
        })

        viewModel.isListBtnVisible.observe(this, Observer {
            putListBtnVisiblePipe.execute(it)
        })

        viewModel.onCleanDB.observe(this, Observer {
            if (it) {
                WorkerManager.deleteAllInDB(this@SettingsFragment) {
                    scopeUI.launch {
                        eraseMovieStorageUseCase.execute(Unit)
                        router.navigateSettingsToStartFragment()
                    }
                }
            }
        })

        viewModel.onChangeBoard.observe(this, Observer {
            if (it) {
                scopeUI.launch {
                    eraseMovieStorageUseCase.execute(Unit)
                    router.navigateSettingsToStartFragment()
                }
            }
        })

        viewModel.isGestureEnabled.observe(this, Observer {
            putIsAllowGesturePipe.execute(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_promote) {
            // navigation to payment
            extensions.showMessage("Whew")
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
