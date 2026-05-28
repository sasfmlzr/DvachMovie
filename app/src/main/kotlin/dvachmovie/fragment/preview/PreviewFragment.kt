package dvachmovie.fragment.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dvachmovie.android.R
import dvachmovie.architecture.base.BaseFragment
import com.dvachmovie.android.databinding.FragmentPreviewMoviesBinding
import com.dvachmovie.android.databinding.FragmentSettingsBinding
import dvachmovie.di.core.FragmentComponent
import javax.inject.Inject

class PreviewFragment : BaseFragment<PreviewVM>(PreviewVM::class) {

    companion object {
        private const val SMOOTH_POSITION = 70
    }

    @Inject
    lateinit var adapter: PreviewMovieAdapter

    private lateinit var binding: FragmentPreviewMoviesBinding

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentPreviewMoviesBinding.inflate(inflater, container, false)
        binding.moviesList.adapter = adapter

        subscribeUi(adapter)
        configureScrollRecyclerView()

        return binding.root
    }

    private fun subscribeUi(adapter: PreviewMovieAdapter) {
        viewModel.uriMovie.observe(viewLifecycleOwner, { movies ->
                    if (movies != null) {
                        adapter.submitList(movies)
                    }
                })
    }

    private fun configureScrollRecyclerView() {
        viewModel.getPosCurrentMovie().let { pos ->
            if (pos < SMOOTH_POSITION) {
                binding.moviesList.smoothScrollToPosition(pos)
            } else {
                binding.moviesList.scrollToPosition(pos)
            }
        }
    }
}
