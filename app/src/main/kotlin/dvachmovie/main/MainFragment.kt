package dvachmovie.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ui.PlayerView
import dvachmovie.base.BaseFragment
import dvachmovie.databinding.MainFragmentBinding
import dvachmovie.di.core.ViewComponent
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: MainFragmentBinding

    override fun inject(component: ViewComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = MainFragmentBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MainViewModel::class.java)
        binding.viewmodel = viewModel

        return binding.root
    }
}
