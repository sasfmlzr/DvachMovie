package dvachmovie.architecture.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dvachmovie.di.core.ActivityComponent
import dvachmovie.di.core.Injector
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<VM : ViewModel>
protected constructor(private val viewModelClass: KClass<VM>) : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    protected abstract fun inject(component: ActivityComponent)

    protected lateinit var viewModel: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(Injector.navigationComponent())
        viewModel = viewModelFactory.create(viewModelClass.java)
    }
}
