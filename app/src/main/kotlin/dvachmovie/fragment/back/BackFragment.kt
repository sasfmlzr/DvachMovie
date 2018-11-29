package dvachmovie.fragment.back

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dvachmovie.R
import dvachmovie.base.BaseFragment
import dvachmovie.databinding.FragmentBackBinding
import dvachmovie.di.core.FragmentComponent
import javax.inject.Inject

class BackFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentBackBinding

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentBackBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(BackViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.imageView.value = resources.getDrawable(R.drawable.kiss)

        return binding.root
    }

}