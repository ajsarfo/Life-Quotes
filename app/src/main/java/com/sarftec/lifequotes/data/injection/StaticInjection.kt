package com.sarftec.lifequotes.data.injection

import android.content.Context
import androidx.room.Room
import com.sarftec.lifequotes.data.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StaticInjection {

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context) : Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "database.db"
        ).build()
    }
}