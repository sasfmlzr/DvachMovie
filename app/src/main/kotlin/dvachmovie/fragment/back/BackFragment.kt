package dvachmovie.fragment.back

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import dvachmovie.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentBackBinding
import dvachmovie.di.core.FragmentComponent

class BackFragment : BaseFragment<BackVM,
        FragmentBackBinding>(BackVM::class.java) {

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBackBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        viewModel.imageView.value = ContextCompat.getDrawable(context!!, R.drawable.kiss)

        binding.imageView.setOnClickListener { activity?.finish() }

        return binding.root
    }

}