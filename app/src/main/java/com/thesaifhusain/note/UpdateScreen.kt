package com.thesaifhusain.note


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.note.R

import com.thesaifhusain.note.DataBase.NoteData
import com.thesaifhusain.note.ViewModel.MainViewModel
import com.thesaifhusain.note.utils.getDate
import com.thesaifhusain.note.utils.getTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    title: String?,
    description: String?,
    mainViewModel: MainViewModel,
    id: Int?,
    navController: NavHostController
) {
    val title1 = remember { mutableStateOf(title) }
    val description1 = remember { mutableStateOf(description) }
    val modContext = LocalContext.current
    val disableEditing = remember { mutableStateOf(true) }
    val alphaValue = remember { mutableStateOf(0.2f) }
    val titleLable= remember { mutableStateOf("Note Title (For edit title Click Green Button)") }
    val textLable= remember { mutableStateOf("Note Text (For edit text Click Green Button)") }
    val openDialogBox= remember { mutableStateOf(false)    }
    val dateAndTime = remember { mutableStateOf("${getDate()}, ${getTime()}") }

    AlertBox(openDialogBox =  openDialogBox, mainViewModel = mainViewModel, id = id!!,
        title =title!! , description = description!!, navController = navController,dateAndTime = dateAndTime.value)
    Scaffold (
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = {
            SmallTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                title = { Text(
                    text = "edit",
                    fontSize = 62.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(2.dp)
                ) },
                actions = {
                    Icon(imageVector = Icons.Default.Close, contentDescription ="close" ,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(35.dp)
                            .clickable {
                                if (disableEditing.value) {
                                    Toast
                                        .makeText(
                                            modContext,
                                            "Enable Editing First (Green Button)",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                } else {
                                    openDialogBox.value = true
                                }
                            })
                    Icon(imageVector = Icons.Default.Save, contentDescription ="save" ,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(35.dp)
                            .clickable {
                                if (disableEditing.value) {
                                    Toast
                                        .makeText(
                                            modContext,
                                            "Enable Editing First",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                } else {
                                    if (title1.value!!.isNotBlank() && description1.value!!.isNotBlank()) {
                                        mainViewModel.vmUpdateManually(
                                            id,
                                            title1.value!!.toString(),
                                            description1.value!!.toString(),
                                            dateAndTime.value
                                        )
                                        Toast
                                            .makeText(
                                                modContext,
                                                "Update Successfully",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                    navController.navigateUp()
                                }
                            })
                    Icon(imageVector = Icons.Default.Edit, contentDescription ="edit" ,
                        tint = Color(0xFF00FA79),
                        modifier = Modifier
                            .padding(5.dp)
                            .size(35.dp)
                            .clickable {
                                Toast
                                    .makeText(
                                        modContext,
                                        "Now Note Is Editable.",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                                titleLable.value = "Update Note Title"
                                textLable.value = "Update Note Text"
                                disableEditing.value = false
                                alphaValue.value = 1f
                            })
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            OutlinedTextField(
                value = title1.value.toString(), onValueChange = { title1.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                readOnly = disableEditing.value,
                singleLine = true,
                label = { Text(text = titleLable.value,color = MaterialTheme.colorScheme.onPrimary)},
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
            OutlinedTextField(
                value = description1.value.toString(), onValueChange = { description1.value = it },
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .padding(14.dp),
                readOnly = disableEditing.value,
                label = { Text(text = textLable.value,color = MaterialTheme.colorScheme.onPrimary) },
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
        }
    }
    
}

@Composable
fun AlertBox(
    openDialogBox: MutableState<Boolean>,
    mainViewModel: MainViewModel,
    id: Int,
    title: String,
    description: String,
    dateAndTime:String,
    navController: NavHostController
)
{
    if (openDialogBox.value)
    { AlertDialog(
        shape = RoundedCornerShape(12.dp),
        onDismissRequest = {
            openDialogBox.value = false
        },
        title = {
            Text(text = "DELETE!")
        },
        text = { Text( "Are You Sure Want To Delete." ) },
        confirmButton = {
            Row(modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center)
            {
                Button(modifier = Modifier.fillMaxWidth(),
                    onClick = { openDialogBox.value = false
                        mainViewModel.vmDelete(NoteData(id, title, description, dateAndTime))
                        navController.navigateUp()
                    }

                )
                {
                    Text("Yes!")
                }
            }
        }
    ) }
}


