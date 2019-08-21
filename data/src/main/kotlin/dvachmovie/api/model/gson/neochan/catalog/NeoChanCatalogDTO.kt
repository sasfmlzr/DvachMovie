package dvachmovie.api.model.gson.neochan.catalog

import com.google.gson.annotations.SerializedName

data class NeoChanCatalogDTO(

        @field:SerializedName("threads")
        val threads: List<ThreadsDTO> = listOf(),

        @field:SerializedName("page")
        val page: Int = 0
)
