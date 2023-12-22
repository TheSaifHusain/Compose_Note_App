package com.thesaifhusain.note

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatAlignLeft
import androidx.compose.material.icons.automirrored.filled.FormatAlignRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thesaifhusain.note.dataBase.NoteData
import com.thesaifhusain.note.utils.getDate
import com.thesaifhusain.note.utils.getTime
import com.thesaifhusain.note.viewModel.MainViewModel
import kotlinx.coroutines.launch

private val state = mutableStateOf(false)
private val upperCaseState = mutableStateOf(false)
private val myTextAlign = mutableStateOf(TextAlign.Left)
private val myFontWeight = mutableStateOf(FontWeight.Normal)
private val myFontDecoration = mutableStateOf<TextDecoration>(TextDecoration.None)

private val isBold2 = mutableStateOf(false)
private val isUnderline2 = mutableStateOf(false)
private val isCenter2 = mutableStateOf(false)
private val isRight2 = mutableStateOf(false)
private val isCapital2 = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UpdateScreen(
    id: Int,
    title: String,
    description: String,
    textSize: Int,
    isCenter: Boolean,
    isRight: Boolean,
    isBold: Boolean,
    isUnderline: Boolean,
    mainViewModel: MainViewModel,
    navController: NavHostController
) {
    myTextAlign.value =
        if (isCenter) TextAlign.Center else if (isRight) TextAlign.Right else TextAlign.Left
    myFontWeight.value = if (isBold) FontWeight.Bold else FontWeight.Normal
    myFontDecoration.value = if (isUnderline) TextDecoration.Underline else TextDecoration.None

    remember { mutableIntStateOf(id) }
    val title1 = remember { mutableStateOf(title) }
    val description1 = remember { mutableStateOf(description) }
    val modContext = LocalContext.current
    val openDialogBox = remember { mutableStateOf(false) }
    val dateAndTime = remember { mutableStateOf("${getDate()}, ${getTime()}") }
    val mySize = remember { mutableIntStateOf(textSize) }

    //this for keyboard https://youtu.be/8waTylS0wUc
    val scope = rememberCoroutineScope()
    LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()

//    Toast.makeText(modContext, "${thisId.intValue}", Toast.LENGTH_SHORT).show()
    AlertBox(
        openDialogBox = openDialogBox,
        mainViewModel = mainViewModel,
        data = NoteData(
            id = id,
            title = title,
            description = description,
            dateAndTime = dateAndTime.value,
            textSize = textSize
        ),
        navController = navController
    )
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = {
            SmallTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        text = "edit",
                        fontSize = 40.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(2.dp)
                    )
                },
                actions = {
                    AssistChip(
                        onClick = { /*TODO*/ },
                        label = {
                            Text(
                                text = mySize.intValue.toString(),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(25.dp)
                                    .clickable {
                                        mySize.intValue = mySize.intValue + 1
                                    }
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(25.dp)
                                    .clickable {
                                        mySize.intValue = mySize.intValue - 1
                                    }
                            )
                        }
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(35.dp)
                            .clickable {
                                openDialogBox.value = true
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "save",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(35.dp)
                            .clickable {
                                if (title1.value.isNotBlank() && description1.value.isNotBlank()) {
                                    mainViewModel.vmUpdateManually(
                                        id = id,
                                        title = title1.value.toString(),
                                        description = description1.value.toString(),
                                        dateAndTime = dateAndTime.value,
                                        textSize = mySize.intValue,
                                        isCenter = isCenter2.value,
                                        isRight = isRight2.value,
                                        isBold = isBold2.value,
                                        isUnderline = isUnderline2.value
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
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
//                .verticalScroll(rememberScrollState())
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = title1.value,
                onValueChange = { title1.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                placeholder = {
                    Text(
                        text = "Please Enter Title",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                singleLine = true,
                label = {
                    Text(text = "Note Title", color = MaterialTheme.colorScheme.onPrimary)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            OutlinedTextField(
                value = description1.value,
                onValueChange = { text ->
                    description1.value = if (upperCaseState.value) text.uppercase() else text
                },
                textStyle = LocalTextStyle
                    .current
                    .copy(textAlign = myTextAlign.value)
                    .copy(fontWeight = myFontWeight.value)
                    .copy(textDecoration = myFontDecoration.value)
                    .copy(fontSize = mySize.intValue.sp),
                trailingIcon = {
                    TextAdvance()
                },
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .padding(14.dp)
                    .onFocusEvent { event ->
                        if (event.isFocused) {
                            scope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    },
                placeholder = {
                    Text(
                        text = "Please enter Your Text",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = {
                    Text(text = "Note Text", color = MaterialTheme.colorScheme.onPrimary)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

@Composable
fun AlertBox(
    data: NoteData,
    openDialogBox: MutableState<Boolean>,
    mainViewModel: MainViewModel,
    navController: NavHostController
) {
    Log.e("data_in_alert", data.toString())
    data.apply {
        if (openDialogBox.value) {
            AlertDialog(
                shape = RoundedCornerShape(12.dp),
                onDismissRequest = {
                    openDialogBox.value = false
                },
                title = {
                    Text(text = "DELETE!")
                },
                text = { Text("Are You Sure Want To Delete.") },
                confirmButton = {
                    Row(
                        modifier = Modifier.padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
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
                                navController.navigateUp()
                            }

                        ) {
                            Text("Yes!")
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun TextAdvance() {
    var boldState by remember { mutableStateOf(false) }
    var underlineState by remember { mutableStateOf(false) }
    var capitalState by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxHeight()) {
        Icon(
            imageVector = Icons.Default.FontDownload,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(5.dp)
                .size(25.dp)
                .clickable {
                    state.value = !state.value
                }
        )
        AnimatedVisibility(visible = state.value) {
            Box(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(20)
                )
            ) {
                Column {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.FormatAlignLeft,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .clickable {
                                myTextAlign.value = TextAlign.Left
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.FormatAlignCenter,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .clickable {
                                myTextAlign.value = TextAlign.Center
                                isCenter2.value = true
                                isRight2.value = false
                            }
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.FormatAlignRight,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .clickable {
                                myTextAlign.value = TextAlign.Right
                                isCenter2.value = false
                                isRight2.value = true
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.FormatBold,
                        contentDescription = "",
                        tint = if (boldState) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        },
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .clickable {
                                boldState = !boldState
                                myFontWeight.value =
                                    if (boldState) FontWeight.Bold else FontWeight.Normal
                                isBold2.value = !isBold2.value
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.FormatUnderlined,
                        contentDescription = "",
                        tint = if (underlineState) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        },
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .clickable {
                                underlineState = !underlineState
                                myFontDecoration.value = if (underlineState) {
                                    TextDecoration.Underline
                                } else {
                                    TextDecoration.None
                                }
                                isUnderline2.value = !isUnderline2.value
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.FormatSize,
                        contentDescription = "",
                        tint = if (capitalState) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        },
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .clickable {
                                capitalState = !capitalState
                                upperCaseState.value = !upperCaseState.value
                                isCapital2.value = !isCapital2.value
                            }
                    )
                }
            }
        }
    }
}
