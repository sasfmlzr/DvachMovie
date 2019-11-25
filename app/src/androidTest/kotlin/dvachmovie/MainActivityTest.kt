package dvachmovie

import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import dvachmovie.activity.start.StartActivity
import dvachmovie.di.DaggerAndroidTestApplicationComponent
import dvachmovie.di.RoomModule
import dvachmovie.di.core.ApplicationModule
import dvachmovie.di.core.Injector
import dvachmovie.di.core.MainApplication
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(StartActivity::class.java,
            false,
            false)

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<MainApplication>()

        val testAppComponent = DaggerAndroidTestApplicationComponent.builder()
                .applicationModule(ApplicationModule(app))
                .roomModule(RoomModule(app))
                .build()
        Injector.component = testAppComponent
        testAppComponent.testComponent().inject(this)
    }

    @Test
    fun uiOnlineTest() {
        activityTestRule.launchActivity(null)

      runBlocking {
          delay(30000)
          activityTestRule.finishActivity()
      }
    }


}