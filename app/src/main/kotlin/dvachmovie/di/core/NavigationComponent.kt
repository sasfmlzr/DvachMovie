package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.activity.main.MainActivity
import dvachmovie.activity.start.StartActivity
import dvachmovie.di.base.ViewScope

@ViewScope
@Subcomponent()
interface NavigationComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: StartActivity)
}