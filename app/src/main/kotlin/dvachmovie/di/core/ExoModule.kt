package dvachmovie.di.core

import android.content.Context
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.Module
import dagger.Provides
import dvachmovie.PresenterModel
import dvachmovie.di.base.FragmentScope
import kotlinx.coroutines.channels.BroadcastChannel

@Module
class ExoModule {

    @Provides
    internal fun provideExoPlayer(context: Context): SimpleExoPlayer =
            ExoPlayerFactory.newSimpleInstance(context)


}
