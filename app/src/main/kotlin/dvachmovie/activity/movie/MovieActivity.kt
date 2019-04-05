package dvachmovie.activity.movie

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import dvachmovie.R
import dvachmovie.architecture.Navigator
import dvachmovie.architecture.base.BaseActivity
import dvachmovie.databinding.ActivityMovieBinding
import dvachmovie.di.core.ActivityComponent
import dvachmovie.repository.local.MovieDBCache
import dvachmovie.worker.WorkerManager
import kotlinx.android.synthetic.main.activity_movie.*
import javax.inject.Inject

class MovieActivity : BaseActivity<MovieActivityVM,
        ActivityMovieBinding>(MovieActivityVM::class) {

    @Inject
    lateinit var movieDBCache: MovieDBCache

    override val layoutId = R.layout.activity_movie

    override fun inject(component: ActivityComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this, "ca-app-pub-3074235676525198~3986251123")

        val request = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("8F3FC8DD5008F6A17A373A2B3DC259FB")
                .build()
        adView.loadAd(request)

        binding.viewModel = viewModel

        movieDBCache.movieList.observe(this, Observer {
            WorkerManager.initDB()
        })
    }

    override fun onSupportNavigateUp() =
            findNavController(this, R.id.navHostFragment).navigateUp()

    override fun onBackPressed() {
        val navController = findNavController(this, R.id.navHostFragment)

        when (navController.currentDestination?.label) {
            "MovieFragment" -> {
                Navigator(NavHostFragment.findNavController(supportFragmentManager
                        .primaryNavigationFragment!!)).navigateMovieToBackFragment()
            }
            "BackFragment" -> {
                Navigator(NavHostFragment.findNavController(supportFragmentManager
                        .primaryNavigationFragment!!)).navigateBackToMovieFragment()
            }
            else -> super.onBackPressed()
        }
    }
}
