package dvachmovie.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import dvachmovie.di.core.Injector
import dvachmovie.di.core.ViewComponent

abstract class BaseFragment : Fragment() {
    protected abstract fun inject(component: ViewComponent)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(Injector.viewComponent())

    }
}