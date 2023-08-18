package com.thesaifhusain.note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.note.R
import com.thesaifhusain.note.DataBase.NoteData
import com.thesaifhusain.note.ViewModel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navHostController: NavHostController, mainViewModel: MainViewModel) {
    val allData = mainViewModel.repository.getList().observeAsState(listOf()).value
    Surface(color = MaterialTheme.colorScheme.primary) {
            Scaffold(
                topBar = {
                         SmallTopAppBar(
                             colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)
                             ,title = {
                             Text(
                                 text = "all notes",
                                 color = MaterialTheme.colorScheme.onPrimary,
                                 fontSize = 62.sp,
                                 modifier = Modifier.padding(2.dp))
                         }
                         )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navHostController.navigate("insertScreen") }
                    ) {
                        Row (Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically){
                            Icon(painter = painterResource(R.drawable.add), contentDescription = "")
                            Text(text = "Add notes",Modifier.padding(5.dp))
                        }
                    }
                },
            ) {

                LazyVerticalGrid(columns = GridCells.Fixed(1),
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding())
                ) {
                    items(allData) { list ->
                        EachRow(
                            list.id,
                            list.title,
                            list.description,
                            navHostController,
                            mainViewModel
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
    description: String,
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    val openDilogBox = remember { mutableStateOf(false) }
    Row (Modifier.padding(12.dp)){
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
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Image(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "note",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Image(
                        imageVector = Icons.Default.Share,
                        contentDescription = "note",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
            , shape = RoundedCornerShape(12.dp),
            onClick = {
                expanded = !expanded
//                navHostController.navigate("updateScreen/$id/$title/$description")
            }
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
                        painter = painterResource(id = R.drawable.cross), contentDescription = "",
                        modifier = Modifier
                            .weight(0.3f)
                            .size(14.dp)
                            .clickable {
                                openDilogBox.value = true
                            },
                        alpha = 0.8f,
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.CenterEnd,
                    )
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .size(1.dp)
                    .background(Color.Black))
                Text(
                    text = "Wednesday 04, 12:14pm",
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
                            modifier = Modifier.selectable(selected = true,enabled = true){}
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
                            mainViewModel.vmDelete(NoteData(id, title, description))
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
