package com.thesaifhusain.note.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thesaifhusain.note.Home
import com.thesaifhusain.note.InsertScreen
import com.thesaifhusain.note.UpdateScreen
import com.thesaifhusain.note.utils.Utils
import com.thesaifhusain.note.viewModel.MainViewModel

@Composable
fun Navigation(mainViewModel: MainViewModel, utils: Utils) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { Home(navHostController = navController, mainViewModel,utils) }
        composable("insertScreen") { InsertScreen(mainViewModel, navController) }
        // Here We Getting Arguments
        composable(
            "updateScreen/{myid}/{title}/{description}/{dateAndTime}/{textSize}/{isTextBold}/{isTextUnderline}/{isTextAlignRight}/{isTextAlignCenter}"
        ) {
            val id = it.arguments?.getString("myid")
            val title = it.arguments?.getString("title")
            val description = it.arguments?.getString("description")
            val dateAndTime = it.arguments?.getString("dateAndTime")
            val textSize = it.arguments?.getString("textSize")
            val isTextBold = it.arguments?.getString("isTextBold")
            val isTextUnderline = it.arguments?.getString("isTextUnderline")
            val isTextAlignRight = it.arguments?.getString("isTextAlignRight")
            val isTextAlignCenter = it.arguments?.getString("isTextAlignCenter")

            UpdateScreen(
                id = id!!.toInt(),
                title = title!!,
                description = description!!,
                textSize = textSize!!.toInt(),
                isCenter = isTextAlignCenter!!.toBoolean(),
                isRight = isTextAlignRight!!.toBoolean(),
                isBold = isTextBold!!.toBoolean(),
                isUnderline = isTextUnderline!!.toBoolean(),
                mainViewModel = mainViewModel,
                navController = navController
            )
        }
    }
}
