package dvachmovie.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding>
protected constructor(private val viewModelClass: Class<VM>) : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract fun inject(component: FragmentComponent)

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(Injector.viewComponent())
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(viewModelClass)
        return view
    }
}