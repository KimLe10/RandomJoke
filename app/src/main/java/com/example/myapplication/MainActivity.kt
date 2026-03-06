package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
        ){
            Column (modifier = Modifier.padding(20.dp)) {

                when(joke) {
                    is JokeState.Error -> {}
                    JokeState.Loading -> CircularProgressIndicator()
                    is JokeState.Success -> {
                        AsyncImage(
                            model = ((joke as JokeState.Success).joke.icon_url),
                            contentDescription = "Image",
                            contentScale = ContentScale.Crop, // Preserves aspect ratio, crops if needed
                            modifier = Modifier
                                .size(300.dp)
                        )
                        Text(text = ((joke as JokeState.Success).joke.value),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium),
                            )
                    }
                }
            }
        }
    }
}

