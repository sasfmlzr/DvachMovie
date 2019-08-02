package dvachmovie.api.model.gson.b4chan.catalog

import com.google.gson.annotations.SerializedName

data class FourChanCatalogDTO(

        @field:SerializedName("threads")
        val threads: List<ThreadsDTO> = listOf(),
        @field:SerializedName("page")
        val page: Int = 0
)