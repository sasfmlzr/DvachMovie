package dvachmovie

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dvachmovie.api.RetrofitSingleton
import dvachmovie.api.model.catalog.DvachCatalogRequest
import dvachmovie.api.model.thread.DvachThreadRequest
import dvachmovie.api.model.thread.FileItem
import dvachmovie.databinding.MainActivityBinding
import dvachmovie.di.core.Injector
import dvachmovie.main.MainFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val BOARD = "b"
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: MainActivityBinding

    private lateinit var dvachMovies: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDI()

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        binding.viewmodel = viewModel

        initMovies()

        //binding.container = MainFragment.newInstance(dvachMovies)
        // binding.viewPager.adapter = MoviesViewPagerAdapter(dvachMovies, supportFragmentManager)
        //  binding.viewPager.offscreenPageLimit=0
        initFragment(dvachMovies)
        getNumThreads(BOARD)
        test()

    }

    private fun initFragment(uriList: MutableList<String>) {
        supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance(uriList))
                .commit()
    }

    private fun initDI() {
        Injector.navigationComponent().inject(this)
    }

    private fun initMovies() {

        dvachMovies = mutableListOf("https://2ch.hk/b/src/185160064/15401994992261.webm",
                "https://2ch.hk/b/src/185159451/15402067914440.webm",
                "https://2ch.hk/b/src/185165705/15402065817600.webm")
    }

    private fun test() {


    }

    private fun getNumThreads(board: String) {
        val dvachApi = RetrofitSingleton.getDvachMovieApi()
        dvachApi?.getCatalog(board)?.enqueue(dvachNumCallback(board))
    }

    private fun dvachNumCallback(board: String): Callback<DvachCatalogRequest> {
        return object : Callback<DvachCatalogRequest> {
            override fun onResponse(call: Call<DvachCatalogRequest>, response: Response<DvachCatalogRequest>) {
                val resp = response.body()
                val numthreads = resp?.threads?.map { it.num }
                println("dvachNum started")
                numthreads?.map { num -> getLinkFilesFromThreads(board, num) }
                println("dvachNum finished")
                count = numthreads!!.size
            }

            override fun onFailure(call: Call<DvachCatalogRequest>, t: Throwable) {}
        }
    }

    private fun getLinkFilesFromThreads(board: String, numThread: String) {
        val dvachApi = RetrofitSingleton.getDvachMovieApi()
        dvachApi?.getThread(board, numThread)?.enqueue(dvachLinkFilesCallback())
    }

    private var listFilesItem = mutableListOf<FileItem>()
    private var count = 0


    private fun dvachLinkFilesCallback(): Callback<DvachThreadRequest> {
        return object : Callback<DvachThreadRequest> {
            override fun onResponse(call: Call<DvachThreadRequest>, response: Response<DvachThreadRequest>) {
                val resp = response.body()

                val num = resp?.title
                println("dvachLinkFiles started for $num")
                resp?.threads?.map { it.posts?.map { it.files?.forEach { listFilesItem.add(it) } } }
                count--
                println("dvachLinkFiles finished for $num")
                if (count == 0) {
                    println("")
                    setupUriVideos()
                }
            }

            override fun onFailure(call: Call<DvachThreadRequest>, t: Throwable) {}
        }
    }

    private var listMovies = mutableListOf<String>()

    private fun setupUriVideos() {
        var count = listFilesItem.size
        listFilesItem.map {
            val path = it.path
            if (path.contains(".webm")) {
                listMovies.add("https://2ch.hk$path")
            }
            count--
            if (count == 0) {
                initWebm()
            }
        }
    }

    private fun initWebm() {
        initFragment(listMovies)
        //   binding.container = MainFragment.newInstance(listMovies)
        //  binding.viewPager.adapter = MoviesViewPagerAdapter(listMovies, supportFragmentManager)
    }

}
