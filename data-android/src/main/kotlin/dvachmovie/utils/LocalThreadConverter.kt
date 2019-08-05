package dvachmovie.utils

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.db.data.Thread

import dvachmovie.db.model.ThreadEntity
import org.joda.time.Instant
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class LocalThreadConverter @Inject constructor() : ThreadConverter {
    override fun convertFileItemToThread(fileItems: List<FileItem>, baseUrl: String): List<Thread> =
            fileItems.map { fileItem ->
                when (baseUrl) {
                    AppConfig.DVACH_URL ->
                        ThreadEntity(
                                date = parseDateFromFileItem(fileItem),
                                thread = fileItem.numThread,
                                nameThread = fileItem.threadName,
                                isHidden = false)
                    AppConfig.FOURCHAN_URL ->
                        ThreadEntity(
                                date = Instant.ofEpochSecond(fileItem.date.toLong())
                                        .toDateTime()
                                        .toLocalDateTime(),
                                thread = fileItem.numThread,
                                nameThread = fileItem.threadName,
                                isHidden = false)
                    else -> throw RuntimeException("I don't know such imageboard")
                }
            }

    private fun parseDateFromFileItem(fileItem: FileItem): LocalDateTime =
            LocalDateTime.parse(fileItem.date,
                    DateTimeFormat.forPattern
                    ("dd/MM/YYYY '${fileItem.date.substring(9, 12)}' HH:mm:ss"))
                    .plusYears(2000)
}
