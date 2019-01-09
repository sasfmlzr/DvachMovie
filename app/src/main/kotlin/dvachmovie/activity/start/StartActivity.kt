package dvachmovie.activity.start

import android.content.Intent
import android.os.Bundle
import dvachmovie.R
import dvachmovie.activity.movie.MovieActivity
import dvachmovie.architecture.base.BaseActivity
import dvachmovie.databinding.ActivityStartBinding
import dvachmovie.di.core.ActivityComponent
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class StartActivity : BaseActivity<StartActivityVM,
        ActivityStartBinding>(StartActivityVM::class.java) {

    override val layoutId = R.layout.activity_start

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
    private val coroutineScope = CoroutineScope(coroutineContext)

    private val MIN_SHOW_TIME: Long = 3000

    override fun inject(component: ActivityComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        initializeApp()
    }

    private fun initializeApp() {
        coroutineScope.launch {
            delay(MIN_SHOW_TIME)
            withContext(Dispatchers.Main) {
                loadingMainActivity()
            }
        }
    }

    private fun loadingMainActivity() {
        val intent = Intent(applicationContext,
                MovieActivity::class.java)
        startActivity(intent)
        finish()
    }

}