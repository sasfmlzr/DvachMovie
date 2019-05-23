package dvachmovie.fragment.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dvachmovie.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.databinding.FragmentPreviewMoviesBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.moviestorage.GetIndexPosByMovieUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PreviewFragment : BaseFragment<PreviewVM,
        FragmentPreviewMoviesBinding>(PreviewVM::class) {

    companion object {
        private const val SMOOTH_POSITION = 70
    }

    @Inject
    lateinit var getIndexPosByMovieUseCase: GetIndexPosByMovieUseCase
    @Inject
    lateinit var adapter: PreviewMovieAdapter

    override fun getLayoutId() = R.layout.fragment_preview_movies

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.viewModel = viewModel
        binding.moviesList.adapter = adapter

        subscribeUi(adapter)
        configureScrollRecyclerView()

        return binding.root
    }

    private fun subscribeUi(adapter: PreviewMovieAdapter) {
        binding.viewModel?.uriMovie
                ?.observe(viewLifecycleOwner, Observer { movies ->
                    if (movies != null) {
                        adapter.submitList(movies)
                    }
                })
    }

    private fun configureScrollRecyclerView() {
        scopeUI.launch {
            viewModel.currentMovie.value?.let {
                getIndexPosByMovieUseCase.execute(it).let { pos ->
                    if (pos < SMOOTH_POSITION) {
                        binding.moviesList.smoothScrollToPosition(pos)
                    } else {
                        binding.moviesList.scrollToPosition(pos)
                    }
                }
            }
        }
    }
}
