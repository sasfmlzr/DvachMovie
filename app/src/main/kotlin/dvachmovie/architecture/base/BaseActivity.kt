package dvachmovie.architecture.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dvachmovie.di.core.Injector
import dvachmovie.di.core.ActivityComponent
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel, B : ViewDataBinding>
protected constructor(private val viewModelClass: Class<VM>) : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract fun inject(component: ActivityComponent)
    protected abstract val layoutId: Int

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(Injector.navigationComponent())
        binding = DataBindingUtil.setContentView(this, layoutId)

        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(viewModelClass)
    }
}
