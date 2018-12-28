package dvachmovie.fragment.movie

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieRepository
import javax.inject.Inject

class MovieVM @Inject constructor(movieRepository: MovieRepository) : ViewModel() {

    val uriMovies: MutableLiveData<MutableList<MovieEntity>> = movieRepository.getMovies()

    val mediaSources = MutableLiveData<ConcatenatingMediaSource>()

    val currentPos = MutableLiveData<Pair<Int, Long>>()

    fun getUrlList() = uriMovies

    val isPlayerControlVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isPlayerControlVisibility.value = true
        mediaSources.value = ConcatenatingMediaSource()
    }

    fun configureMediaSources(playerView: PlayerView): ConcatenatingMediaSource {
        val urlVideo: List<Uri> = uriMovies.value!!.map { value -> Uri.parse(value.movieUrl) }

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(playerView.context,
                Util.getUserAgent(playerView.context, "AppName"))

        val videoSources = urlVideo.map { url ->
            ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(url) as MediaSource
        } as MutableList<MediaSource>

        val mediaSources = ConcatenatingMediaSource()

        videoSources.map { url -> mediaSources.addMediaSource(url) }
        return mediaSources
    }
}