package dvachmovie.slices

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.SliceAction

class DvachSliceProvider : SliceProvider() {

    override fun onCreateSliceProvider(): Boolean = true


    /**
     * Converts URL to content URI (i.e. content://com.dvachmovie.android.pro...)
     */
    override fun onMapIntentToUri(intent: Intent?): Uri {
        // Note: implementing this is only required if you plan on catching URL requests.
        // This is an example solution.
        var uriBuilder: Uri.Builder = Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
        if (intent == null) return uriBuilder.build()
        val data = intent.data
	val dataPath = data?.path
        if (data != null && dataPath != null) {
            val path = dataPath.replace("/", "")
            uriBuilder = uriBuilder.path(path)
        }
        val context = context
        if (context != null) {
            uriBuilder = uriBuilder.authority(context.packageName)
        }
        return uriBuilder.build()
    }

    /**
     * Construct the Slice and bind data if available.
     */
    override fun onBindSlice(sliceUri: Uri): Slice {

        return lastSlices.getOrPut(sliceUri){
            createNewSlice(sliceUri)
        }.getSlice()

    }

    private val lastSlices = mutableMapOf<Uri, DvachSlice>()

    private fun createNewSlice(sliceUri: Uri): DvachSlice {
        val context = requireNotNull(context) {
            "SliceProvider $this not attached to a context."
        }
        return when (sliceUri.path) {

            DeepLink.STATS -> FitStatsSlice(
                    context = context,
                    sliceUri = sliceUri
            )
            else -> DvachSlice.Unknown(context, sliceUri)
        }
    }
}
