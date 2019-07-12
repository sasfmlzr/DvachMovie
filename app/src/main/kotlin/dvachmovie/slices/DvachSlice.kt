/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package dvachmovie.slices

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.core.graphics.drawable.IconCompat
import androidx.slice.Slice
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.SliceAction
import androidx.slice.builders.list
import androidx.slice.builders.row
import dvachmovie.R
import dvachmovie.activity.movie.MovieTestActivity

/**
 * Base class that defines a Slice for the app.
 *
 * Every slice implementation should extend this class and implement getSlice method.
 */
abstract class DvachSlice(val context: Context, val sliceUri: Uri) {

    /**
     * Main thread handler to execute tasks that requires Android Main Thread
     */
    protected val handler = Handler(Looper.getMainLooper())

    /**
     * @return the specific slice implementation to be used by SliceProvider
     */
    abstract fun getSlice(): Slice

    /**
     * Call refresh to notify the SliceProvider to load again.
     */
    protected fun refresh() {
        context.contentResolver.notifyChange(sliceUri, null)
    }

    /**
     * Utility method to create a SliceAction that launches the main activity.
     */
    protected fun createActivityAction(): SliceAction {
        val intent = Intent(context, MovieTestActivity::class.java)
        return SliceAction.create(
                PendingIntent.getActivity(context, 0, intent, 0),
                IconCompat.createWithResource(context, R.mipmap.ic_launcher),
                ListBuilder.SMALL_IMAGE,
                "Whew"
        )
    }

    /**
     * Default implementation of FitSlice when the uri could not be handled.
     */
    class Unknown(context: Context, sliceUri: Uri) : DvachSlice(context, sliceUri) {

        override fun getSlice(): Slice = list(context, sliceUri, ListBuilder.INFINITY) {
            // Adds a row to the slice
            row {
                // Set the title of the row
                title = "Not found"
                // Defines the action when slice is clicked.
                primaryAction = createActivityAction()
            }

            // Mark the slice as error type slice.
            setIsError(true)
        }
    }
}

object DeepLink {
    const val STATS = "/stats"
    const val START = "/start"
    const val STOP = "/stop"
    const val FLEX = "/flex"
    /**
     * Parameter types for the deep-links
     */
    object Params {
        const val ACTIVITY_TYPE = "exerciseType"
    }

    object Actions {
        const val ACTION_TOKEN_EXTRA = "actions.fulfillment.extra.ACTION_TOKEN"
    }
}