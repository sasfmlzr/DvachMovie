package dvachmovie.fragment.back

import android.annotation.SuppressLint
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
        FragmentBackBinding>(BackVM::class) {

    override fun inject(component: FragmentComponent) = component.inject(this)

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBackBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        viewModel.imageView.value = ContextCompat.getDrawable(context!!, R.raw.kiss)

        binding.imageView.setOnClickListener { activity?.finish() }

        return binding.root
    }
}
