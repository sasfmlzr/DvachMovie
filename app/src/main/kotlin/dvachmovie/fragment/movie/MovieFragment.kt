package dvachmovie.fragment.movie

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.TIMELINE_CHANGE_REASON_RESET
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import dvachmovie.Utils.DirectoryHelper
import dvachmovie.WRITE_EXTERNAL_STORAGE_REQUEST_CODE
import dvachmovie.base.BaseFragment
import dvachmovie.databinding.FragmentMovieBinding
import dvachmovie.db.data.MovieEntity
import dvachmovie.di.core.FragmentComponent
import dvachmovie.listener.OnSwipeTouchListener
import dvachmovie.repository.local.MovieRepository
import dvachmovie.service.DownloadService
import javax.inject.Inject

class MovieFragment : BaseFragment<MovieVM,
        FragmentMovieBinding>(MovieVM::class.java) {

    @Inject
    lateinit var movieRepository: MovieRepository

    private lateinit var player: PlayerView

    override fun inject(component: FragmentComponent) = component.inject(this)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentMovieBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(viewLifecycleOwner)

        player = binding.playerView
        val simplePlayer: SimpleExoPlayer =
                ExoPlayerFactory.newSimpleInstance(player.context)
        player.player = simplePlayer
        configurePlayer()

        player.setOnTouchListener(onGestureListener())

        viewModel.currentPos.value = movieRepository.getPos()

        binding.shuffleButton.setOnClickListener {
            movieRepository.getMovies().value =
                    movieRepository.getMovies().value!!.shuffled() as MutableList<MovieEntity>
        }

        binding.downloadButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
            }
        }

        return binding.root
    }

    override fun onStop() {
        if (movieRepository.getMovies().value?.size != 0) {
            movieRepository.getCurrent().value = movieRepository.getMovies().value!![player.player.currentWindowIndex]
        }
        player.player.stop()

        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                DirectoryHelper.createDirectory(context!!)
            movieRepository.getCurrent().value =
                    movieRepository.getMovies().value!![player.player.currentWindowIndex]
            activity?.startService(DownloadService.getDownloadService(
                    context!!,
                    movieRepository.getCurrent().value!!.movieUrl,
                    DirectoryHelper.ROOT_DIRECTORY_NAME + "/"))
        }
    }

    private fun onGestureListener(): OnSwipeTouchListener {
        return object : OnSwipeTouchListener(context!!) {
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View, event: MotionEvent): Boolean {

                if (event.action == MotionEvent.ACTION_DOWN) {
                    toggleControlsVisibility()
                }
                return super.onTouch(v, event)
            }

            override fun onSwipeTop() {
                val movieUri = binding.viewModel!!.getUrlList().value?.get(player.player.currentPeriodIndex)
                movieRepository.getCurrent().value = movieUri
                val direction = MovieFragmentDirections
                        .ActionShowPreviewFragment()
                findNavController(this@MovieFragment).navigate(direction)
            }
        }
    }

    private fun configurePlayer() {
        movieRepository.posPlayer = player.player.currentWindowIndex
        player.player.addListener(object : Player.EventListener {

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == PlaybackStateCompat.STATE_ERROR) {
                    println("ERROR")
                }
            }

            override fun onPlayerError(error: ExoPlaybackException?) {
                print("onPlayerError")
            }

            override fun onPositionDiscontinuity(reason: Int) {
                val pos = player.player.currentWindowIndex
                if (reason == TIMELINE_CHANGE_REASON_RESET) {
                    println("FUCKING Position is $pos")

                    if (movieRepository.posPlayer == pos - 1) {
                        println("FORWARD POS ---------------------")
                        movieRepository.posPlayer = pos
                    }

                    if (movieRepository.posPlayer == pos + 1) {
                        println("PREW POS ---------------------")
                        movieRepository.posPlayer = pos
                    }
                }

            }
        })
    }

    private fun toggleControlsVisibility() {
        if (player.isControllerVisible) {
            binding.shuffleButton.visibility = INVISIBLE
            binding.downloadButton.visibility = INVISIBLE
            player.hideController()
        } else {
            binding.shuffleButton.visibility = VISIBLE
            binding.downloadButton.visibility = VISIBLE
            player.showController()
        }
    }
}