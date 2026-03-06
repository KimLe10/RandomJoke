package com.example.myapplication.repository

import com.example.myapplication.remote.JokesAPI
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class JokesRepository @Inject constructor(private val api: JokesAPI)
{
    suspend fun getJoke() = withContext(Dispatchers.IO){
        api.getRandomJoke()
    }
}