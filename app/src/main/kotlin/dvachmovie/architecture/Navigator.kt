package dvachmovie.architecture

import androidx.navigation.NavController
import dvachmovie.architecture.logging.Logger
import dvachmovie.fragment.back.BackFragmentDirections
import dvachmovie.fragment.movie.MovieFragmentDirections
import dvachmovie.fragment.preview.PreviewFragmentDirections
import dvachmovie.fragment.settings.SettingsFragmentDirections
import dvachmovie.fragment.start.StartFragmentDirections

class Navigator(private val router: NavController,
                private val logger: Logger) {

    fun navigateStartToMovieFragment() {
        val direction = StartFragmentDirections
                .actionShowMovieFragment()
        try {
            router.navigate(direction)
        } catch (e: Exception) {
            logger.e("Already attached")
        }
    }

    fun navigateSettingsToStartFragment() {
        val direction = SettingsFragmentDirections
                .actionShowStartFragment()
        router.navigate(direction)
    }

    fun navigatePreviewToMovieFragment() {
        val direction = PreviewFragmentDirections
                .actionShowMovieFragment()
        router.navigate(direction)
    }

    fun navigateMovieToPreviewFragment() {
        val direction = MovieFragmentDirections
                .actionShowPreviewFragment()
        router.navigate(direction)
    }

    fun navigateMovieToSettingsFragment() {
        val direction = MovieFragmentDirections
                .actionShowSettingsFragment()
        router.navigate(direction)
    }

    fun navigateMovieToBackFragment() {
        val direction = MovieFragmentDirections
                .actionShowBackFragment()
        router.navigate(direction)
    }

    fun navigateBackToMovieFragment() {
        val direction = BackFragmentDirections
                .actionShowMovieFragment()
        router.navigate(direction)
    }
}
