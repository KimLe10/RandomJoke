package com.example.myapplication.remote

import androidx.room.Query
import com.example.myapplication.data.JokesResponse
import retrofit2.http.GET

// https://api.chucknorris.io/jokes/random
interface JokesAPI {
    @GET("jokes/random")
    suspend fun getRandomJoke(): JokesResponse
}