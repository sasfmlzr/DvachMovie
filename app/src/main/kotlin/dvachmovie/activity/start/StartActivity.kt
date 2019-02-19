package dvachmovie.activity.start

import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import dvachmovie.R
import dvachmovie.activity.movie.MovieActivity
import dvachmovie.architecture.base.BaseActivity
import dvachmovie.databinding.ActivityStartBinding
import dvachmovie.di.core.ActivityComponent
import dvachmovie.worker.WorkerManager
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class StartActivity : BaseActivity<StartActivityVM,
        ActivityStartBinding>(StartActivityVM::class) {

    companion object {
        const val MIN_SHOW_TIME: Long = 2000
    }

    override val layoutId = R.layout.activity_start

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
    private val coroutineScope = CoroutineScope(coroutineContext)

    override fun inject(component: ActivityComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        if (savedInstanceState == null) {
            initializeApp()
        }
    }

    override fun onStart() {
        super.onStart()
        WorkerManager.loadContactsToNetwork()
    }

    private fun initializeApp() {
        coroutineScope.launch {
            delay(MIN_SHOW_TIME)
            loadingMainActivity()
        }
    }

    private fun loadingMainActivity() {
        val intent = Intent(applicationContext,
                MovieActivity::class.java)
        startActivity(intent)
        finish()
    }
}
