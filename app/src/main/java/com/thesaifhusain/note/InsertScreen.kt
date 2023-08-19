package com.thesaifhusain.note

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thesaifhusain.note.DataBase.NoteData
import com.thesaifhusain.note.ViewModel.MainViewModel
import com.thesaifhusain.note.ui.theme.PreBayazTheme
import com.thesaifhusain.note.utils.getDate
import com.thesaifhusain.note.utils.getTime

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun InsertScreen(mainViewModel: MainViewModel, navHostController:NavHostController) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val dateAndTime = remember { mutableStateOf("${getDate()}, ${getTime()}") }
    val modContext= LocalContext.current
    Scaffold (
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = {
            SmallTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                title = { Text(
                    text = "save",
                    fontSize = 62.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(2.dp)
                ) },
                actions = {
                    Icon(imageVector = Icons.Default.Save, contentDescription ="edit" ,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(35.dp)
                            .clickable {
                                Toast
                                    .makeText(
                                        modContext,
                                        "Note Is Saved.",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                                if (title.value.isNotBlank() && description.value.isNotBlank()) {
                                    mainViewModel.vmInsert(
                                        NoteData(
                                            0,
                                            title.value,
                                            description.value,
                                            dateAndTime.value
                                        )
                                    )
                                    Toast
                                        .makeText(
                                            modContext,
                                            "Added Successfully",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                    navHostController.navigateUp()
                                } else
                                    Toast
                                        .makeText(
                                            modContext,
                                            "Please Insert Your Note",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()

                            })
                }
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            OutlinedTextField(value = title.value, onValueChange = { title.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                placeholder = { Text(text = "Please Enter Title",color = MaterialTheme.colorScheme.onPrimary) },
                singleLine = true,
                label = { Text(text = "Note Tilte",color = MaterialTheme.colorScheme.onPrimary) },
                colors = TextFieldDefaults
                    .outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                    )
            )
            OutlinedTextField(value = description.value,
                onValueChange = { description.value = it },
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .padding(14.dp),
                placeholder = { Text(text = "Please enter Your Text",color = MaterialTheme.colorScheme.onPrimary) },
                label = { Text(text = "Note Text", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TextFieldDefaults
                    .outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                    ))
        }
    }
    }


@Preview
@Composable
fun prev()
{

}