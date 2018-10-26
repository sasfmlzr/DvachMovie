package dvachmovie.di.core

import android.content.Context
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExoModule {

    @Provides
    @Singleton
    internal fun provideExoplayer(context: Context): SimpleExoPlayer =
            ExoPlayerFactory.newSimpleInstance(context)

}