package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.MainActivity
import dvachmovie.di.base.ViewScope

@ViewScope
@Subcomponent()
interface NavigationComponent {
    fun inject(activity: MainActivity)
}