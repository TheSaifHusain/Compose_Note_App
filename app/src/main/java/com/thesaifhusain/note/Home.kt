package com.thesaifhusain.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Surface(Modifier.background(MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "All Notes!", fontSize = 30.sp, modifier = Modifier.padding(14.dp))
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navHostController.navigate("insertScreen") },
//                    backgroundColor = Color.Blue,
                        modifier = Modifier.size(52.dp)
                    ) {
                        Icon(painter = painterResource(R.drawable.add), contentDescription = "")
                    }
                },
            ) {
                val paddingValues = it
                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
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
    val openDilogBox = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxWidth(), shape = RoundedCornerShape(12.dp),
        onClick = {
            navHostController.navigate("updateScreen/$id/$title/$description")
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
                        .padding(2.dp)
                        .weight(3f),
                    fontWeight = FontWeight.Bold, fontSize = 22.sp, text = title
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

            OutlinedTextField(
                value = description,
                readOnly = true,
                onValueChange = { description },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )
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
            }
        )
    }
}
