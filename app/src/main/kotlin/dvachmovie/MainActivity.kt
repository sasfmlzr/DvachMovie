package dvachmovie

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dvachmovie.di.core.Injector
import dvachmovie.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initDI()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    private fun initDI() {
        Injector.navigationComponent().inject(this)
    }

}
