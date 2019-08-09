package dvachmovie.architecture

import androidx.navigation.NavController
import androidx.navigation.NavDirections
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
        navigate(direction)
    }

    fun navigateSettingsToStartFragment(isRefresh: Boolean) {
        val direction = SettingsFragmentDirections
                .actionShowStartFragment(isRefresh)
        navigate(direction)
    }

    fun navigatePreviewToMovieFragment() {
        val direction = PreviewFragmentDirections
                .actionShowMovieFragment()
        navigate(direction)
    }

    fun navigateMovieToPreviewFragment() {
        val direction = MovieFragmentDirections
                .actionShowPreviewFragment()
        navigate(direction)
    }

    fun navigateMovieToSettingsFragment() {
        val direction = MovieFragmentDirections
                .actionShowSettingsFragment()
        navigate(direction)
    }

    fun navigateMovieToBackFragment() {
        val direction = MovieFragmentDirections
                .actionShowBackFragment()
        navigate(direction)
    }

    fun navigateMovieToSelf() {
        val direction = MovieFragmentDirections
                .actionShowMovieFragmentSelf()
        navigate(direction)
    }

    fun navigateBackToMovieFragment() {
        val direction = BackFragmentDirections
                .actionShowMovieFragment()
        navigate(direction)
    }
    
    private fun navigate(direction: NavDirections) {
        try {
            logger.d(router.currentDestination.toString())
            router.navigate(direction)
        } catch (e: RuntimeException) {
            logger.e("Already attached")
        }
    }
}
