package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myapplication.data.JokesResponse
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.JokeState
import com.example.myapplication.viewmodel.JokeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                JokeScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeScreen (viewModel: JokeViewModel = hiltViewModel()) {

    val joke by viewModel.joke.observeAsState(JokeState.Loading)

    Scaffold(
        topBar = { TopAppBar({Text("Joke of the Day")})},
        modifier = Modifier.fillMaxSize()
    )
    { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .padding(20.dp)
        ){
            Column (modifier = Modifier) {

                when(joke) {
                    is JokeState.Error -> {}
                    JokeState.Loading -> CircularProgressIndicator()
                    is JokeState.Success -> {
                        AsyncImage(
                            model = ((joke as JokeState.Success).joke.icon_url),
                            contentDescription = "Image"
                        )
                        Text(text = ((joke as JokeState.Success).joke.value))
                    }
                }

            }
        }
    }
}

