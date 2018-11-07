package dvachmovie.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ui.PlayerView
import dvachmovie.base.NewFragment
import dvachmovie.databinding.MainFragmentBinding
import dvachmovie.di.core.ViewComponent
import java.util.*

class MainFragment : NewFragment() {
    private val TAG = "APP"

    private lateinit var playerView: PlayerView

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun inject(component: ViewComponent) = component.inject(this)

    companion object {

        fun newInstance(uriList: MutableList<String>): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle(1)
            bundle.putStringArrayList("uri", uriList as ArrayList<String>?)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val uri = this.arguments?.getStringArrayList("uri") ?: mutableListOf<String>()

        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.loadUri(uri)
        binding.viewmodel = viewModel

        playerView = binding.playerView

        return binding.root
    }

    fun pauseVideo() {
        playerView.onPause()
    }

    override fun onPause() {
        playerView.player.stop()
        super.onPause()
    }

    override fun onStop() {
        playerView.player.stop()
        super.onStop()
    }
}
