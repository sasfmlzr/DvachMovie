package com.sasfmlzr.distillery.dvachmovie.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.sasfmlzr.distillery.dvachmovie.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var player : SimpleExoPlayer
    private lateinit var playerView: PlayerView

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding : MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewmodel = viewModel

        player = ExoPlayerFactory.newSimpleInstance(context)
        playerView = binding.playerView
        playerView.player = player;
        
        return binding.root
    }
}
