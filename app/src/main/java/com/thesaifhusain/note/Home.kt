package com.thesaifhusain.note

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thesaifhusain.note.DataBase.NoteData
import com.thesaifhusain.note.ViewModel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navHostController: NavHostController, mainViewModel: MainViewModel) {
    val allData = mainViewModel.repository.getList().observeAsState(listOf()).value
    Surface(color = MaterialTheme.colorScheme.primary) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "all notes",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 62.sp,
                            modifier = Modifier.padding(2.dp)
                        )
                    },
                    colors = topAppBarColors(
                        MaterialTheme.colorScheme.primary
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navHostController.navigate("insertScreen") }
                ) {
                    Row(
                        Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "")
                        Text(text = "Add notes", Modifier.padding(5.dp))
                    }
                }
            },
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
            ) {
                items(allData) { list ->
                    EachRow(
                        id = list.id,
                        title = list.title,
                        description = list.description,
                        dateAndTime = list.dataAndTime,
                        navHostController = navHostController,
                        mainViewModel = mainViewModel
                    )
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachRow(
    id: Int,
    title: String,
    dateAndTime: String,
    description: String,
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val openDilogBox = remember { mutableStateOf(false) }
    Row(Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)

        ) {
            Image(
                imageVector = Icons.Default.Notes,
                contentDescription = "note",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),

                )
            AnimatedVisibility(visible = expanded) {
                Column {
                    Image(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "note",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .clickable {
                                navHostController.navigate("updateScreen/$id/$title/$description")
                            }
                    )
                    Image(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "note",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .clickable {
                                val clipboardManager = context.getSystemService(
                                    Context.CLIPBOARD_SERVICE
                                ) as ClipboardManager
                                val clipData =
                                    ClipData.newPlainText(
                                        "text",
                                        "${title}\n${description}\n${dateAndTime}"
                                    )
                                clipboardManager.setPrimaryClip(clipData)
                                Toast
                                    .makeText(
                                        context,
                                        "Text copied to clipboard",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            }
                    )
                    Image(
                        imageVector = Icons.Default.Share,
                        contentDescription = "note",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_SEND)
                                intent.type = "text/plain"
                                intent.putExtra(
                                    Intent.EXTRA_TEXT,
                                    "$title\n$description\n$dateAndTime"
                                ) //your Image Url
                                context.startActivity(Intent.createChooser(intent, "Share Text"))
                            }
                    )
                }
            }
        }
        Card(
            onClick = {
                expanded = !expanded
//                navHostController.navigate("updateScreen/$id/$title/$description")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row {
                    Text(
                        modifier = Modifier

                            .weight(3f),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        text = title
                    )

                    Image(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "close",
                        modifier = Modifier
                            .weight(0.3f)
                            .size(25.dp)
                            .clickable {
                                openDilogBox.value = true
                            },
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.CenterEnd,
                        colorFilter = ColorFilter.tint(Color(0xFFFA021A))
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(1.dp)
                        .background(Color.Black)
                )
                Text(
                    text = dateAndTime,
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    }
                )

                AnimatedVisibility(visible = expanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = description,
                            color = Color.Black,
                            modifier = Modifier.selectable(selected = true, enabled = true) {}
                        )
                    }
                }


//                OutlinedTextField(
//                    value = description,
//                    readOnly = true,
//                    onValueChange = { description },
//                    modifier = Modifier.fillMaxWidth(),
//                    maxLines = 3
//                )
            }

        }
    }


    if (openDilogBox.value) {
        AlertDialog(
            shape = RoundedCornerShape(12.dp),
            onDismissRequest = {
                openDilogBox.value = false
            },
            title = {
                Text(text = "DELETE!")
            },
            text = { Text("Are You Sure Want To Delete.") },
            confirmButton = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Button(modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            openDilogBox.value = false
                            mainViewModel.vmDelete(NoteData(id, title, description, dateAndTime))
                        })
                    {
                        Text("Yes!")
                    }
                }
            },
            dismissButton = {
                Row(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Button(modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            openDilogBox.value = false
                        })
                    {
                        Text("No")
                    }
                }
            }
        )
    }
}
