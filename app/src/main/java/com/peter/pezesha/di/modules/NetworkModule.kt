package com.peter.pezesha.di.modules

import android.content.Context
import androidx.room.Room
import com.peter.pezesha.data.db.appdatabase.AppDatabase
import com.peter.pezesha.data.db.dao.ProductDao
import com.peter.pezesha.data.network.dummyjson.datasource.DummyJsonApi
import com.peter.pezesha.utils.providers.StringResourceProvider
import com.pezesha.BuildConfig

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG && BuildConfig.BUILD_TYPE.contains(DEBUG_MARKER)) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "product_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao = database.productDao()
    @Provides
    fun provideCameraApiHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideDummyJsonApi(
        stringResourceProvider: StringResourceProvider,
        httpClient: OkHttpClient,
    ): DummyJsonApi {

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        }

        return Retrofit.Builder()
            .baseUrl(stringResourceProvider.getDummyJsonApiBaseUrl())
            .client(httpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
            .create(DummyJsonApi::class.java)
    }

    private companion object {
        const val DEBUG_MARKER = "debug"
        const val CONNECTION_TIMEOUT = 10L
    }
}
