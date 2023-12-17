package com.thesaifhusain.note

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thesaifhusain.note.dataBase.NoteData
import com.thesaifhusain.note.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navHostController: NavHostController, mainViewModel: MainViewModel) {
    val allData = mainViewModel.repository.getList().observeAsState(listOf()).value
    val deleteAllAlert = remember { mutableStateOf(false) }
    Surface(color = MaterialTheme.colorScheme.primary) {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "all notes",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 40.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                },
                colors = topAppBarColors(
                    MaterialTheme.colorScheme.primary
                ),
                actions = {
                    ElevatedAssistChip(
                        onClick = {
                            deleteAllAlert.value = !deleteAllAlert.value
                        },
                        label = {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = "",
                                modifier = Modifier.size(28.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.padding(end = 14.dp, top = 5.dp)
                    )
                }
            )
        }, floatingActionButton = {
            FloatingActionButton(onClick = { navHostController.navigate("insertScreen") }) {
                Row(
                    Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                    Text(text = "Add notes", Modifier.padding(5.dp))
                }
            }
        }) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.background(MaterialTheme.colorScheme.primary).fillMaxSize()
                    .padding(top = it.calculateTopPadding())
            ) {
                items(allData) { list ->
                    Log.e("DATA", list.toString())
                    val myId = remember { mutableStateOf(list.id) }
                    EachRow(
                        id = myId.value,
                        title = list.title,
                        description = list.description,
                        dateAndTime = list.dateAndTime,
                        textSize = list.textSize,
                        isTextAlignCenter = list.isTextAlignCenter,
                        isTextAlignRight = list.isTextAlignRight,
                        isTextBold = list.isTextBold,
                        isTextUnderline = list.isTextUnderline,
                        navHostController = navHostController,
                        mainViewModel = mainViewModel
                    )
                }
            }
            if (deleteAllAlert.value) {
                AlertDialog(
                    shape = RoundedCornerShape(12.dp),
                    onDismissRequest = {
                        deleteAllAlert.value = false
                    },
                    title = {
                        Text(text = "DELETE ALL?")
                    },
                    text = { Text("Are You Sure Want To Delete ALL.") },
                    confirmButton = {
                        Row(
                            modifier = Modifier.padding(all = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                                deleteAllAlert.value = false
                                mainViewModel.deleteAll()
                            }) {
                                Text("Yes!")
                            }
                        }
                    },
                    dismissButton = {
                        Row(
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                                deleteAllAlert.value = false
                            }) {
                                Text("No")
                            }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachRow(
    id: Int,
    title: String,
    description: String,
    dateAndTime: String,
    textSize: Int,
    isTextAlignCenter: Boolean,
    isTextAlignRight: Boolean,
    isTextBold: Boolean,
    isTextUnderline: Boolean,
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    var myid by remember { mutableStateOf(id) }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val openDialogBox = remember { mutableStateOf(false) }
    Row(Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier.background(Color.Transparent)
        ) {
            Image(
                imageVector = Icons.AutoMirrored.Filled.Notes,
                contentDescription = "note",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
            )
            AnimatedVisibility(visible = expanded) {
                Column {
                    Image(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "note",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp).clickable {
                            navHostController.navigate(
                                "updateScreen/$myid/$title/$description/$dateAndTime/$textSize/$isTextBold/$isTextUnderline/$isTextAlignRight/$isTextAlignCenter"
                            )
                        }
                    )
                    Image(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "note",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp).clickable {
                            val clipboardManager = context.getSystemService(
                                Context.CLIPBOARD_SERVICE
                            ) as ClipboardManager
                            val clipData = ClipData.newPlainText(
                                "text",
                                "${title}\n${description}\n$dateAndTime"
                            )
                            clipboardManager.setPrimaryClip(clipData)
                            Toast.makeText(
                                context,
                                "Text copied to clipboard",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                    Image(
                        imageVector = Icons.Default.Share,
                        contentDescription = "note",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp).clickable {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            intent.putExtra(
                                Intent.EXTRA_TEXT,
                                "$title\n$description\n$dateAndTime"
                            ) // your Image Url
                            context.startActivity(
                                Intent.createChooser(
                                    intent,
                                    "Share Text"
                                )
                            )
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
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(5.dp)
            ) {
                Row {
                    Text(
                        modifier = Modifier.weight(3f),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        text = title
                    )

                    Image(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "close",
                        modifier = Modifier.weight(0.3f).size(25.dp).clickable {
                            openDialogBox.value = true
                        },
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.CenterEnd,
                        colorFilter = ColorFilter.tint(Color(0xFFFA021A))
                    )
                }
                Spacer(
                    modifier = Modifier.fillMaxWidth().size(1.dp).background(Color.Black)
                )
                Text(
                    text = dateAndTime,
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    }
                )

                AnimatedVisibility(visible = expanded) {
                    Column(
                        modifier = Modifier.fillMaxWidth().background(Color.LightGray)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = description,
                            color = Color.Black,
                            fontSize = textSize.sp,
                            textAlign = if (isTextAlignCenter) TextAlign.Center else if (isTextAlignRight) TextAlign.Right else TextAlign.Left,
                            textDecoration = if (isTextUnderline) TextDecoration.Underline else TextDecoration.None,
                            fontWeight = if (isTextBold) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.fillMaxWidth()
                                .selectable(selected = true, enabled = true) {}
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

    if (openDialogBox.value) {
        Log.e("IDDDD", id.toString())
        AlertDialog(shape = RoundedCornerShape(12.dp), onDismissRequest = {
            openDialogBox.value = false
        }, title = {
            Text(text = "DELETE!")
        }, text = { Text("Are You Sure Want To Delete.") }, confirmButton = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    openDialogBox.value = false
                    mainViewModel.vmDelete(
                        NoteData(
                            id,
                            title,
                            description,
                            dateAndTime,
                            textSize,
                            isTextAlignCenter,
                            isTextAlignRight,
                            isTextBold,
                            isTextUnderline
                        )
                    )
                }) {
                    Text("Yes!")
                }
            }
        }, dismissButton = {
            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    openDialogBox.value = false
                }) {
                    Text("No")
                }
            }
        })
    }
}
