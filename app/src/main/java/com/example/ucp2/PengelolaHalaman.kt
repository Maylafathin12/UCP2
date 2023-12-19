package com.example.ucp2

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.ucp2.data.SumberData.dosen
import halawal
import halform

enum class PengelolaHalaman {
    Home,
    Form,
    Detail
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun formApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    Scaffold { innerPadding ->
        val uiState by viewModel.stateUI.collectAsState()
        NavHost(
            navController = navController,
            startDestination = PengelolaHalaman.Home.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(PengelolaHalaman.Home.name){
                halawal(
                    onNextButtonClicked = { navController.navigate(PengelolaHalaman.Form.name)
                    })}
            composable(PengelolaHalaman.Form.name){
                val context = LocalContext.current
                halform(
                    dosenPilihan = dosen.map{ id -> context.resources.getString(id)},
                    onSelectionChanged = { viewModel.setDosen(it)},
                    onPilihChanged = { viewModel.setDosen2(it)},
                    onSubmitBUttonClicked = {
                        viewModel.setContact(it)
                        navController.navigate(PengelolaHalaman.Detail.name)},
                )}
            composable(
                route = PengelolaHalaman.Detail.name){
                haldetail(orderUIState = uiState, onCancelButtonClicked = { cancelOrderAndNavigateToForm(navController) },
                )
            }
        }
    }
}
private fun cancelOrderAndNavigateToForm(
    navController: NavHostController
){
    navController.popBackStack(PengelolaHalaman.Form.name, inclusive = false)
}

