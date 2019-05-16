package dvachmovie.fragment.settings

import android.os.Bundle
import android.text.InputFilter
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
import dvachmovie.storage.SettingsStorage
import dvachmovie.storage.local.MovieStorage
import dvachmovie.worker.WorkerManager
import kotlinx.android.synthetic.main.include_settings_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsFragment : BaseFragment<SettingsVM,
        FragmentSettingsBinding>(SettingsVM::class) {

    @Inject
    lateinit var settingsStorage: SettingsStorage
    @Inject
    lateinit var movieStorage: MovieStorage

    private val scope = CoroutineScope(Dispatchers.Main)

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

        val filters = arrayOfNulls<InputFilter>(1)
        filters[0] = InputFilter { source, start, end, dest, dstart, dend ->
            if (end > start) {
                val destTxt = dest.toString()
                val resultingTxt = destTxt.substring(0, dstart) +
                        source.subSequence(start, end) +
                        destTxt.substring(dend)
                if (!resultingTxt.matches(("^\\d{1,3}(\\." +
                                "(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\:(\\d{1,5})?)?)?)?)?)?)?)?").toRegex())) {
                    return@InputFilter ""
                } else {
                    var port = ""
                    val splits = if (resultingTxt.contains(":")) {
                        val list = resultingTxt.split(":")
                        port = list.last()
                        list.first().split(".").dropLastWhile { it.isEmpty() }
                    } else {
                        resultingTxt.split(".").dropLastWhile { it.isEmpty() }
                    }

                    if (port != "") {
                        if (Integer.valueOf(port) > 65536) {
                            return@InputFilter ""
                        }
                    }
                    for (i in splits.indices) {
                        if (Integer.valueOf(splits[i]) > 255) {
                            return@InputFilter ""
                        }
                    }
                }
            }
            null
        }
        proxy_edit_text.filters = filters
    }

    private fun setUpToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.setSupportActionBar(toolbar)
    }

    private fun configureVM() {
        viewModel.prepareLoading.observe(this, Observer {
            scope.launch {
                withContext(Dispatchers.Default) {
                    settingsStorage.putLoadingEveryTime(it)
                }
            }
        })

        viewModel.isReportBtnVisible.observe(this, Observer {
            scope.launch {
                withContext(Dispatchers.Default) {
                    settingsStorage.putReportBtnVisible(it)
                }
            }
        })

        viewModel.isListBtnVisible.observe(this, Observer {
            scope.launch {
                withContext(Dispatchers.Default) {
                    settingsStorage.putListBtnVisible(it)
                }
            }
        })

        viewModel.isProxyEnabled.observe(this, Observer {
            scope.launch {
                withContext(Dispatchers.Default) {
                    settingsStorage.putIsProxyEnabled(it)
                }
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
            scope.launch {
                withContext(Dispatchers.Default) {
                    settingsStorage.putIsAllowGesture(it)
                }
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
