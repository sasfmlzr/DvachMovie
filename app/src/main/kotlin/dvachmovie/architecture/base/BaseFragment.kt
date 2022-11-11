package dvachmovie.architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dvachmovie.architecture.Extensions
import dvachmovie.architecture.Navigator
import dvachmovie.architecture.logging.Logger
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding>
protected constructor(private val viewModelClass: KClass<VM>) : Fragment() {

    companion object {
        private const val permissionRequestCode = 100
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    @Inject
    lateinit var logger: Logger

    protected abstract fun inject(component: FragmentComponent)

    protected abstract fun getLayoutId(): Int
    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    protected lateinit var extensions: Extensions
    protected lateinit var router: Navigator

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(Injector.viewComponent())
        lifecycle.addObserver(MyServer(logger))
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        viewModel = viewModelFactory.create(viewModelClass.java)

        return view
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        extensions = Extensions(activity as AppCompatActivity)
        router = Navigator(findNavController(), logger)
    }

    val runtimePermissions = object : RuntimePermissions {
        override fun request(permissions: List<String>) {
            requestPermissions(permissions.toTypedArray(), permissionRequestCode)
        }

        override fun request(permission: String) {
            request(listOf(permission))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != permissionRequestCode) {
            return
        }

        val grantedPermissions = mutableListOf<String>()

        if (permissions.isNotEmpty() && grantResults.isNotEmpty()) {
            for ((index, value) in grantResults.withIndex()) {
                grantedPermissions.add(permissions[index])
            }
        }

        (this as? PermissionsCallback)?.onPermissionsGranted(grantedPermissions)
    }
}
