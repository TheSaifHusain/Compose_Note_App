package com.thesaifhusain.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.thesaifhusain.note.Navigation.Navigation
import com.thesaifhusain.note.ViewModel.MainViewModel
import com.thesaifhusain.note.ui.theme.PreBayazTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreBayazTheme {
                window.navigationBarColor = MaterialTheme.colorScheme.primary.toArgb()
                Column {
                    Navigation(viewModel)

                }
            }


        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PreBayazTheme {

    }
}