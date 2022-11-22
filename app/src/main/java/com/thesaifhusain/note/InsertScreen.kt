package com.thesaifhusain.note

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.thesaifhusain.note.DataBase.NoteData
import com.thesaifhusain.note.ViewModel.MainViewModel
import com.thesaifhusain.note.ui.theme.PreBayazTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun InsertScreen(mainViewModel: MainViewModel, navHostController:NavHostController) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val modContext= LocalContext.current
    PreBayazTheme{
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            OutlinedTextField(value = title.value, onValueChange = { title.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp), placeholder = { Text(text = "Please Enter Title") },
                singleLine = true,
                label = { Text(text = "Note Tilte") })
            OutlinedTextField(value = description.value,
                onValueChange = { description.value = it },
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .padding(14.dp),
                placeholder = { Text(text = "Please enter Your Text") },
                label = { Text(text = "Note Text") })
            Button(
                onClick = {
                    if (title.value.isNotBlank() && description.value.isNotBlank()) {
                        mainViewModel.vmInsert(NoteData(0, title.value, description.value))
                        Toast.makeText(modContext, "Added Successfully", Toast.LENGTH_SHORT).show()
                        navHostController.navigateUp()
                    } else
                        Toast.makeText(modContext, "Please Insert Your Note", Toast.LENGTH_SHORT)
                            .show()

                }, modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth()) {
                Text(text = "Save")
            }
        }
    }
}

@Preview
@Composable
fun prev()
{

}