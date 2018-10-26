package dvachmovie.di.core

import android.content.Context
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.Module
import dagger.Provides

@Module
class ExoModule {

    @Provides
    internal fun provideExoplayer(context: Context): SimpleExoPlayer =
            ExoPlayerFactory.newSimpleInstance(context)

}