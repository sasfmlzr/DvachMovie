package dvachmovie.architecture

import androidx.navigation.NavController
import dvachmovie.fragment.start.StartFragmentDirections

class Navigator(private val router: NavController) {

    fun navigateStartToMovieFragment() {
        val direction = StartFragmentDirections
                .ActionShowMovieFragment()
        router.navigate(direction)
    }
}