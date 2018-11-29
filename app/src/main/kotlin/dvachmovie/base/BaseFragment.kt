package dvachmovie.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import dvachmovie.di.core.Injector
import dvachmovie.di.core.FragmentComponent

abstract class BaseFragment : Fragment() {
    protected abstract fun inject(component: FragmentComponent)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(Injector.viewComponent())

    }
}