package com.example.fitchef.di

import android.content.Context
import androidx.room.Room
import com.example.fitchef.data.room.FoodsBasketDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDBModule {

    @Provides
    @Singleton
    fun provideRoomDB(
        @ApplicationContext context: Context
    ): FoodsBasketDB = Room.databaseBuilder(
        context,
        FoodsBasketDB::class.java,
        "foodsbasketdatabase.db"
    ).allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideFoodsBasketDAO(
        foodsBasketDB: FoodsBasketDB
    ) = foodsBasketDB.foodBasketDAOInterface()
}