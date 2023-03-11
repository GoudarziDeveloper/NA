package com.tinyDeveloper.na.di

import android.content.Context
import com.tinyDeveloper.na.data.remote.NaApi
import com.tinyDeveloper.na.data.repositories.data_store.DataStoreImpl
import com.tinyDeveloper.na.data.repositories.remote_data_surce.*
import com.tinyDeveloper.na.domain.repositories.*
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(45, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNaApi(retrofit: Retrofit): NaApi{
        return retrofit.create(NaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersSource(naApi: NaApi): UsersSource{
        return UsersSourceImpl(naApi = naApi)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore{
        return DataStoreImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideNotificationsSource(naApi: NaApi): NotificationsSource{
        return NotificationsSourceImpl(naApi = naApi)
    }

    @Provides
    @Singleton
    fun provideJobsSource(naApi: NaApi): JobsSource{
        return JobsSourceImpl(naApi = naApi)
    }

    @Provides
    @Singleton
    fun provideFilesSource(naApi: NaApi): FilesSource{
        return FilesSourceImpl(naApi = naApi)
    }

    @Provides
    @Singleton
    fun provideSubmissionsSource(naApi: NaApi): SubmissionsSource{
        return SubmissionsSourceImpl(naApi = naApi)
    }

    @Provides
    @Singleton
    fun provideBaseInfoSource(naApi: NaApi): BaseInfoSource{
        return BaseInfoSourceImpl(naApi = naApi)
    }

    @Provides
    @Singleton
    fun provideBasesSource(naApi: NaApi): BasesSource{
        return BasesSourceImpl(naApi = naApi)
    }
}