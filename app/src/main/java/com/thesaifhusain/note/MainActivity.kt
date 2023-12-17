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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.thesaifhusain.note.navigation.Navigation
import com.thesaifhusain.note.ui.theme.PreBayazTheme
import com.thesaifhusain.note.viewModel.MainViewModel
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

        //this is for keyboard handling from https://youtu.be/8waTylS0wUc
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)){ view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom)
            insets
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PreBayazTheme {
    }
}
