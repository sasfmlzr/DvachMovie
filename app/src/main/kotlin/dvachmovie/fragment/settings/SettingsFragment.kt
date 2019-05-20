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
import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.settingsStorage.PutIsAllowGestureUseCase
import dvachmovie.usecase.settingsStorage.PutIsListBtnVisibleUseCase
import dvachmovie.usecase.settingsStorage.PutIsLoadingEveryTimeUseCase
import dvachmovie.usecase.settingsStorage.PutIsReportBtnVisibleUseCase
import dvachmovie.worker.WorkerManager
import kotlinx.android.synthetic.main.include_settings_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsFragment : BaseFragment<SettingsVM,
        FragmentSettingsBinding>(SettingsVM::class) {

    @Inject
    lateinit var movieStorage: MovieStorage

    @Inject
    lateinit var putLoadingEveryTimeUseCase: PutIsLoadingEveryTimeUseCase

    @Inject
    lateinit var putReportBtnVisibleUseCase: PutIsReportBtnVisibleUseCase

    @Inject
    lateinit var putListBtnVisibleUseCase: PutIsListBtnVisibleUseCase

    @Inject
    lateinit var putIsAllowGestureUseCase: PutIsAllowGestureUseCase

    override fun getLayoutId() = R.layout.fragment_settings

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        configureVM()

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
            scopeUI.launch {
                putLoadingEveryTimeUseCase.execute(it)
            }
        })

        viewModel.isReportBtnVisible.observe(this, Observer {
            scopeUI.launch {
                    putReportBtnVisibleUseCase.execute(it)
            }
        })

        viewModel.isListBtnVisible.observe(this, Observer {
            scopeUI.launch {
                    putListBtnVisibleUseCase.execute(it)
            }
        })

        viewModel.onCleanDB.observe(this, Observer {
            if (it) {
                WorkerManager.deleteAllInDB(this) {
                    movieStorage.movieList.value = listOf()
                    router.navigateSettingsToStartFragment()
                }
            }
        })

        viewModel.onChangeBoard.observe(this, Observer {
            if (it) {
                movieStorage.movieList.value = listOf()
                router.navigateSettingsToStartFragment()
            }
        })

        viewModel.isGestureEnabled.observe(this, Observer {
            scopeUI.launch {
                    putIsAllowGestureUseCase.execute(it)
            }
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
