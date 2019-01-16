package dvachmovie.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentSettingsBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.usecase.SettingsUseCase
import dvachmovie.worker.WorkerManager
import javax.inject.Inject

class SettingsFragment : BaseFragment<SettingsVM,
        FragmentSettingsBinding>(SettingsVM::class.java) {

    @Inject
    lateinit var settingsUseCase: SettingsUseCase

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        viewModel.prepareLoading.observe(this, Observer {
            settingsUseCase.putLoadingParam(it)
        })

        viewModel.onRefreshDB.observe(this, Observer {
            if (it) {
                WorkerManager.deleteAllInDB(this) {
                    navigateSettingsToStartFragment()
                }
            }
        })

        viewModel.getContactClick = {
            navigateSettingsToContactsFragment()
        }

        val activity = (activity as AppCompatActivity)
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }


    private fun navigateSettingsToStartFragment() {
        val direction = SettingsFragmentDirections.ActionShowStartFragment()
        findNavController().navigate(direction)
    }

    private fun navigateSettingsToContactsFragment() {
        val direction = SettingsFragmentDirections
                .ActionShowContactsFragment()
        findNavController().navigate(direction)
    }
}