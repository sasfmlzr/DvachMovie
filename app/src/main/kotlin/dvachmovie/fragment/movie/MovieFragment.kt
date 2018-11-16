package dvachmovie.fragment.movie

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dvachmovie.base.BaseFragment
import dvachmovie.databinding.MainFragmentBinding
import dvachmovie.di.core.ViewComponent
import javax.inject.Inject

class MovieFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: MainFragmentBinding

    override fun inject(component: ViewComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = MainFragmentBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MovieViewModel::class.java)
        binding.viewmodel = viewModel

        return binding.root
    }
}
