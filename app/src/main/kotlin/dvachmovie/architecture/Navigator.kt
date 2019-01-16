package dvachmovie.architecture

import androidx.navigation.NavController
import dvachmovie.fragment.movie.MovieFragmentDirections
import dvachmovie.fragment.preview.PreviewFragmentDirections
import dvachmovie.fragment.settings.SettingsFragmentDirections
import dvachmovie.fragment.start.StartFragmentDirections

class Navigator(private val router: NavController) {

    fun navigateStartToMovieFragment() {
        val direction = StartFragmentDirections
                .ActionShowMovieFragment()
        router.navigate(direction)
    }

    fun navigateSettingsToStartFragment() {
        val direction = SettingsFragmentDirections.ActionShowStartFragment()
        router.navigate(direction)
    }

    fun navigateSettingsToContactsFragment() {
        val direction = SettingsFragmentDirections
                .ActionShowContactsFragment()
        router.navigate(direction)
    }

    fun navigatePreviewToMovieFragment() {
        val direction = PreviewFragmentDirections.ActionShowMovieFragment()
        router.navigate(direction)
    }

    fun navigateMovieToPreviewFragment() {
        val direction = MovieFragmentDirections
                .ActionShowPreviewFragment()
        router.navigate(direction)
    }

    fun navigateMovieToSettingsFragment() {
        val direction = MovieFragmentDirections
                .ActionShowSettingsFragment()
        router.navigate(direction)
    }
}