package dvachmovie.main

import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
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

    private lateinit var player: SimpleExoPlayer
    private lateinit var playerView: PlayerView

    override fun inject(component: ViewComponent) = component.inject(this)

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        var urlVideo: Uri = Uri.parse("https://2ch.hk/b/src/184743141/15396153482850.webm")
        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewmodel = viewModel

        player = ExoPlayerFactory.newSimpleInstance(context)
        playerView = binding.playerView
        playerView.player = player

        var dataSourceFactory : DataSource.Factory = DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "AppName"))
        var videoSource : MediaSource = ExtractorMediaSource.Factory(dataSourceFactory).
                createMediaSource(urlVideo)
        player.prepare(videoSource)
        return binding.root
    }
}
