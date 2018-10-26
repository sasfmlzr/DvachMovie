package dvachmovie.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MoviesViewPagerAdapter(private var uriMovieList: List<String>,
                             fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment {
        return MainFragment.newInstance(uriMovieList.get(p0))
    }

    override fun getCount(): Int {
        return uriMovieList.size
    }

}