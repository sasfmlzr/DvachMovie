package dvachmovie.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ui.PlayerView
import dvachmovie.base.NewFragment
import dvachmovie.databinding.MainFragmentBinding
import dvachmovie.di.core.ViewComponent

class MainFragment : NewFragment() {
    private val TAG = "APP"

    private lateinit var playerView: PlayerView

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun inject(component: ViewComponent) = component.inject(this)

    companion object {

        fun newInstance(uri: String): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle(1)
            bundle.putString("uri", uri)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val uri = this.arguments?.getString("uri") ?: ""

        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.loadUri(uri)
        binding.viewmodel = viewModel

        playerView = binding.playerView

        return binding.root
    }

    fun pauseVideo(){
        playerView.onPause()
    }

}
