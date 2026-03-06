package com.example.myapplication.viewmodel

import com.example.myapplication.data.JokesResponse

sealed class JokeState () {

    object Loading: JokeState()
    data class Success(val joke: JokesResponse): JokeState()
    data class Error(val message: String): JokeState()
}