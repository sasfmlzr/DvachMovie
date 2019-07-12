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

import android.content.Context
import android.net.Uri
import androidx.slice.Slice
import androidx.slice.builders.CellBuilderDsl
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.cell
import androidx.slice.builders.gridRow
import androidx.slice.builders.header
import androidx.slice.builders.list
import dvachmovie.activity.movie.MovieTestActivity
import java.util.*

/**
 * Slice that loads the last user activities and stats, and displays them once loaded.
 *
 * This class shows how to deal with Slices that needs to load content asynchronously.
 */
class FitStatsSlice(
        context: Context,
        sliceUri: Uri
) : DvachSlice(context, sliceUri) {
    override fun getSlice(): Slice {
        println()
        return createActivity()
    }

    private fun createActivity(): Slice {
        return list(context, sliceUri, ListBuilder.INFINITY) {
            header {
                title = "SSSSSSSSSSSSSS"
                subtitle = "ffffffffffffff"

                primaryAction = createActivityAction()
            }

            gridRow {
                cell {

                }
            }
        }
    }

    /**
     * Given a Slice cell, setup the content to display the given FitActivity.
     */
    private fun CellBuilderDsl.setFitActivity(value: MovieTestActivity) {
        val distanceInKm = String.format("%.2f", 4500 / 1000)
        val distance = "wtggggghdlkdj"
        addText(distance)

        val calendar = Calendar.getInstance().apply { timeInMillis = 10 }
        addTitleText(
                calendar.getDisplayName(
                        Calendar.DAY_OF_WEEK,
                        Calendar.LONG,
                        Locale.getDefault()
                )
        )
    }


}
