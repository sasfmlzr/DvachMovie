package dvachmovie.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import dvachmovie.AppConfig
import com.dvachmovie.android.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.architecture.binding.BindingCache
import com.dvachmovie.android.databinding.FragmentSettingsBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.worker.WorkerManager

class SettingsFragment : BaseFragment<SettingsVM,
        FragmentSettingsBinding>(SettingsVM::class) {

    override fun getLayoutId() = R.layout.fragment_settings

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
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
            BindingCache.media = listOf()
            WorkerManager.deleteAllInDB(requireContext(), this) {
                viewModel.reInitMovies(false)
            }
        }
        viewModel.setReportBtnVisible()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.root.findViewById(R.id.toolbar))
        viewModel.version.observe(viewLifecycleOwner) {
            binding.versionText.text = it
        }
    }
}
