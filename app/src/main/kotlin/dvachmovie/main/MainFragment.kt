package dvachmovie.main

import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dvachmovie.base.NewFragment
import dvachmovie.databinding.MainFragmentBinding
import dvachmovie.di.core.ViewComponent

class MainFragment : NewFragment() {
    private lateinit var URI_MOVIE: String

    private lateinit var player: SimpleExoPlayer
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

        URI_MOVIE = this.arguments?.getString("uri") ?: ""
        val urlVideo: Uri = Uri.parse(URI_MOVIE)

        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewmodel = viewModel

        player = ExoPlayerFactory.newSimpleInstance(context)
        playerView = binding.playerView
        playerView.player = player

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "AppName"))
        val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(urlVideo)
        player.prepare(videoSource)

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        playerView.onPause()
    }
}
