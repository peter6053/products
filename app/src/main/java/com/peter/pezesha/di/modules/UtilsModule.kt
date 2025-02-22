package com.peter.pezesha.di.modules

import android.content.Context
import com.peter.pezesha.utils.providers.StringResourceProvider
import com.peter.pezesha.utils.providers.StringResourceProviderContextImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    @Singleton
    fun provideStringResourceProvider(
        @ApplicationContext context: Context,
    ): StringResourceProvider = StringResourceProviderContextImpl(context = context)
}
