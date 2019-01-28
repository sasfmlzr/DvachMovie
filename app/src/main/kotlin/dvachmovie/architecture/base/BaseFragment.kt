package dvachmovie.architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import dvachmovie.architecture.Extensions
import dvachmovie.architecture.Navigator
import dvachmovie.architecture.logging.Logger
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding>
protected constructor(private val viewModelClass: Class<VM>) : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var logger: Logger

    protected abstract fun inject(component: FragmentComponent)

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    protected lateinit var extensions: Extensions
    protected lateinit var router: Navigator

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

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        extensions = Extensions(activity as AppCompatActivity)
        router = Navigator(findNavController())
    }
}
