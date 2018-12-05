package dvachmovie.usecase

import dvachmovie.Constraints
import dvachmovie.api.DvachMovieApi
import dvachmovie.api.RetrofitSingleton
import dvachmovie.api.model.catalog.DvachCatalogRequest
import dvachmovie.api.model.thread.DvachThreadRequest
import dvachmovie.api.model.thread.FileItem
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.db.MovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DvachUseCase @Inject constructor(private val dvachApis: DvachMovieApi,
                                       private val movieRepository: MovieRepository) {

    private var listFilesItem = mutableListOf<FileItem>()
    private var listMovies = mutableListOf<MovieEntity>()
    private var listMovieSize = 0
    private var count = 0
    private var board: String = ""
    private lateinit var initWebm: InitWebm

    fun getNumThreads(board: String, initWebm: InitWebm) {
        this.initWebm = initWebm
        this.board = board
        dvachApis.getCatalog(board).enqueue(dvachNumCallback(board))
    }

    private fun dvachNumCallback(board: String): Callback<DvachCatalogRequest> {
        return object : Callback<DvachCatalogRequest> {
            override fun onResponse(call: Call<DvachCatalogRequest>, response: Response<DvachCatalogRequest>) {
                val resp = response.body()
                val numThreads = resp?.threads?.map { it.num }
                println("dvachNum started")
                numThreads?.map { num -> getLinkFilesFromThreads(board, num) }
                println("dvachNum finished")
                listMovieSize = numThreads!!.size
                initWebm.countVideoCalculatedSumm(listMovieSize)
            }

            override fun onFailure(call: Call<DvachCatalogRequest>, t: Throwable) {}
        }
    }

    private fun getLinkFilesFromThreads(board: String, numThread: String) {
        val dvachApi = RetrofitSingleton.getDvachMovieApi()
        dvachApi?.getThread(board, numThread)?.enqueue(dvachLinkFilesCallback())
    }

    private fun dvachLinkFilesCallback(): Callback<DvachThreadRequest> {
        return object : Callback<DvachThreadRequest> {
            override fun onResponse(call: Call<DvachThreadRequest>, response: Response<DvachThreadRequest>) {
                val resp = response.body()

                val num = resp?.title
                println("dvachLinkFiles started for $num")
                resp?.threads?.map { it.posts?.map { it.files?.forEach { listFilesItem.add(it) } } }
                count++
                initWebm.countVideoUpdates(count)
                println("dvachLinkFiles finished for $num")
                if (count == listMovieSize) {
                    setupUriVideos()
                }
            }

            override fun onFailure(call: Call<DvachThreadRequest>, t: Throwable) {}
        }
    }

    private fun setupUriVideos() {
        var count = listFilesItem.size
        listFilesItem.map {
            val path = it.path
            if (path.contains(".webm")) {
                val movieEntity = MovieEntity(board = this.board,
                        movieUrl = Constraints.BASE_URL + path,
                        previewUrl = Constraints.BASE_URL + it.thumbnail)
                listMovies.add(movieEntity)
            }
            count--
            if (count == 0) {
                initWebm()
            }
        }
    }

    private fun initWebm() {
        movieRepository.insertAll(listMovies)
        initWebm.initWebm()
    }
}