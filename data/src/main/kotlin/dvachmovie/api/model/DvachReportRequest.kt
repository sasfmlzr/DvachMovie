package dvachmovie.api.model

import com.google.gson.annotations.SerializedName

data class DvachReportRequest(@SerializedName("message")
                              val message: String = "",
                              @SerializedName("message_title")
                              val messageTitle: String = "")
