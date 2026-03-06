package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.JokesResponse
import com.example.myapplication.repository.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class JokeViewModel @Inject constructor(private val jokesRepo: JokesRepository): ViewModel() {
    private val _joke : MutableLiveData<JokeState> = MutableLiveData(JokeState.Loading)
    val joke: LiveData<JokeState> = _joke

    init {
        showJoke()
    }

    fun showJoke(){
        viewModelScope.launch {
            _joke.value = JokeState.Loading
            val result = jokesRepo.getJoke()
            if (result.id != null) {
                _joke.value = JokeState.Success(result)
            } else {
                _joke.value = JokeState.Error("Joke not found!")
            }
        }
    }
}