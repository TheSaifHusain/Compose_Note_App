package com.thesaifhusain.note.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thesaifhusain.note.Home
import com.thesaifhusain.note.InsertScreen
import com.thesaifhusain.note.UpdateScreen
import com.thesaifhusain.note.ViewModel.MainViewModel

@Composable
fun Navigation(mainViewModel: MainViewModel)
{
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "home" ){
        composable("home"){ Home(navHostController = navController, mainViewModel) }
        composable("insertScreen"){ InsertScreen(mainViewModel,navController) }
        //Here We Getting Arguments
        composable("updateScreen/{id}/{title}/{description}"){
            val id = it.arguments?.getString("id")
            val title = it.arguments?.getString("title")
            val description = it.arguments?.getString("description")
            UpdateScreen(title=title,description=description, mainViewModel = mainViewModel,id!!.toInt(),navController) }
    }

}
