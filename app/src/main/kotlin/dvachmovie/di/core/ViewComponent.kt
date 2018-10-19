package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.di.base.ViewScope
import dvachmovie.main.MainFragment

@ViewScope
@Subcomponent()
interface ViewComponent {
    fun inject(mainFragment: MainFragment)
}