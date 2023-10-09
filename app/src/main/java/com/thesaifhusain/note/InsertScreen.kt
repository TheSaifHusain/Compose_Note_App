package com.thesaifhusain.note

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thesaifhusain.note.dataBase.NoteData
import com.thesaifhusain.note.utils.getDate
import com.thesaifhusain.note.utils.getTime
import com.thesaifhusain.note.viewModel.MainViewModel

private val state = mutableStateOf(false)
private val upperCaseState = mutableStateOf(false)
private val myTextAlign = mutableStateOf(TextAlign.Left)
private val myFontWeight = mutableStateOf(FontWeight.Normal)
private val myFontDecoration = mutableStateOf<TextDecoration>(TextDecoration.None)

private val isBold = mutableStateOf(false)
private val isUnderline = mutableStateOf(false)
private val isCenter = mutableStateOf(false)
private val isRight = mutableStateOf(false)
private val isCapital = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun InsertScreen(mainViewModel: MainViewModel, navHostController: NavHostController) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val dateAndTime = remember { mutableStateOf("${getDate()}, ${getTime()}") }
    val modContext = LocalContext.current
    val mySize = remember { mutableIntStateOf(16) }
//     val myFontSize =  remember{ mutableStateOf(mySize.value.sp)}
    Log.i("zz", mySize.intValue.toString())
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = {
            SmallTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        text = "save",
                        fontSize = 62.sp,
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
                        imageVector = Icons.Default.Save,
                        contentDescription = "edit",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(35.dp)
                            .clickable {
//                                Toast
//                                    .makeText(
//                                        modContext,
//                                        "Note Is Saved.",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                    .show()
                                if (title.value.isNotBlank() && description.value.isNotBlank()) {
                                    mainViewModel.vmInsert(
                                        NoteData(
                                            id = 0,
                                            title = title.value,
                                            description = description.value,
                                            dateAndTime = dateAndTime.value,
                                            textSize = mySize.value,
                                            isTextAlignCenter = isCenter.value,
                                            isTextAlignRight = isRight.value,
                                            isTextBold = isBold.value,
                                            isTextUnderline = isUnderline.value
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
                                } else {
                                    Toast
                                        .makeText(
                                            modContext,
                                            "Please Insert Your Note",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
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
                value = title.value,
                onValueChange = { title.value = it },
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
                label = { Text(text = "Note Tilte", color = MaterialTheme.colorScheme.onPrimary) },
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
                value = description.value,
                onValueChange = {
                    description.value = if (upperCaseState.value) it.uppercase() else it
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
                    .padding(14.dp),
                placeholder = {
                    Text(
                        text = "Please enter Your Text",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = { Text(text = "Note Text", color = MaterialTheme.colorScheme.onPrimary) },
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

@OptIn(ExperimentalMaterial3Api::class)
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
                        imageVector = Icons.Default.FormatAlignLeft,
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
                                isCenter.value = true
                                isRight.value = false
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.FormatAlignRight,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .clickable {
                                myTextAlign.value = TextAlign.Right
                                isCenter.value = false
                                isRight.value = true
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
                                isBold.value = !isBold.value
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
                                isUnderline.value = !isUnderline.value
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
                                isCapital.value = !isCapital.value
                            }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun kk() {
    TextAdvance()
}
