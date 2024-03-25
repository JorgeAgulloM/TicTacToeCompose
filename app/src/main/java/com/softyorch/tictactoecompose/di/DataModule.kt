package com.softyorch.tictactoecompose.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.softyorch.tictactoecompose.data.network.FirebaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providesDatabaseReference() = Firebase.database.reference

    @Singleton
    @Provides
    fun providesFirebaseServices(databaseRef: DatabaseReference) = FirebaseService(databaseRef)
}
