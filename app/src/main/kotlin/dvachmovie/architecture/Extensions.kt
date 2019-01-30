package dvachmovie.architecture

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar

class Extensions(
        private val activity: FragmentActivity
) {
    fun showMessage(text: String): Unit? =
            activity.findViewById<View>(android.R.id.content)
                    ?.let { Snackbar.make(it, text, Snackbar.LENGTH_LONG).show() }
}
