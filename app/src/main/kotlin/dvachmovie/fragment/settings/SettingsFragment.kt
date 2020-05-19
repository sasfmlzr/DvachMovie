package dvachmovie.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import dvachmovie.AppConfig
import dvachmovie.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentSettingsBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.worker.WorkerManager
import kotlinx.android.synthetic.main.include_settings_fragment.*

class SettingsFragment : BaseFragment<SettingsVM,
        FragmentSettingsBinding>(SettingsVM::class) {

    override fun getLayoutId() = R.layout.fragment_settings

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.addFields()

        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        configureVM()

        return binding.root
    }

    private fun configureVM() {
        when (viewModel.getCurrentBaseUrl()) {
            AppConfig.DVACH_URL -> {
                viewModel.isDvachBoardsVisible.value = true
                viewModel.isFourChanBoardsVisible.value = false
                viewModel.isNeoChanBoardsVisible.value = false
            }
            AppConfig.FOURCHAN_URL -> {
                viewModel.isDvachBoardsVisible.value = false
                viewModel.isFourChanBoardsVisible.value = true
                viewModel.isNeoChanBoardsVisible.value = false
            }

            AppConfig.NEOCHAN_URL -> {
                viewModel.isDvachBoardsVisible.value = false
                viewModel.isFourChanBoardsVisible.value = false
                viewModel.isNeoChanBoardsVisible.value = true
            }
        }

        viewModel.routeToStartFragment = {
            router.navigateSettingsToStartFragment(it)
        }

        viewModel.recreateMoviesDB = {
            logger.d(this.javaClass.name, "refresh database")
            WorkerManager.deleteAllInDB(requireContext(), this) {
                viewModel.reInitMovies(false)
            }
        }
        viewModel.setReportBtnVisible()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
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
