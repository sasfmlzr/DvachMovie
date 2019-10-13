package dvachmovie.architecture.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dvachmovie.di.core.ActivityComponent
import dvachmovie.di.core.Injector
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<VM : ViewModel, B : ViewDataBinding>
protected constructor(private val viewModelClass: KClass<VM>) : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    protected abstract fun inject(component: ActivityComponent)
    protected abstract val layoutId: Int

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(Injector.navigationComponent())
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        viewModel = viewModelFactory.create(viewModelClass.java)
    }
}
