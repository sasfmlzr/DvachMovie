package dvachmovie.fragment.movie

import dvachmovie.di.core.FragmentComponent

class MovieFragment : MovieBaseFragment() {
    override val containsAds = false

    override fun inject(component: FragmentComponent) = component.inject(this)
}
