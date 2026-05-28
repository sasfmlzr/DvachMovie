package dvachmovie.fragment.back

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dvachmovie.android.R
import dvachmovie.architecture.base.BaseFragment
import com.dvachmovie.android.databinding.FragmentBackBinding
import com.dvachmovie.android.databinding.FragmentPreviewMoviesBinding
import dvachmovie.di.core.FragmentComponent

class BackFragment : BaseFragment<BackVM>(BackVM::class) {

    override fun inject(component: FragmentComponent) = component.inject(this)
    private lateinit var binding: FragmentBackBinding
    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBackBinding.inflate(inflater, container, false)

        viewModel.imageId.value = R.raw.kiss

        binding.imageView.setOnClickListener {
            activity?.finish()
            viewModel.eraseMovieStorage()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.imageId.observe(viewLifecycleOwner) { image ->
            Glide.with(binding.imageView)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageView)
        }
    }
}
