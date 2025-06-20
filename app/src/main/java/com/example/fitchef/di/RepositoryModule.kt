package com.example.fitchef.di

import com.example.fitchef.data.repos.FoodsRepository
import com.example.fitchef.data.repos.UserRepository
import com.example.fitchef.data.retrofit.FoodsService
import com.example.fitchef.data.room.FoodsBasketDAO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideFoodsRepository(
        foodsService: FoodsService,
        foodsDAO: FoodsBasketDAO
    ) = FoodsRepository(foodsService, foodsDAO)

    @Provides
    @Singleton
    fun provideUsersRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ) =UserRepository(auth, firestore)
}