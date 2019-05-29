package dvachmovie.fragment.back

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dvachmovie.PresenterModel
import dvachmovie.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentBackBinding
import dvachmovie.di.core.FragmentComponent

class BackFragment : BaseFragment<BackVM,
        FragmentBackBinding>(BackVM::class) {

    override fun getLayoutId() = R.layout.fragment_back

    override fun inject(component: FragmentComponent) = component.inject(this)

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.imageId.value = R.raw.kiss

        binding.imageView.setOnClickListener { activity?.finish() }

        return binding.root
    }
}
