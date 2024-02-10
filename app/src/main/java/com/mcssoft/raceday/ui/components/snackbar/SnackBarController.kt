package com.mcssoft.raceday.ui.components.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

// https://www.youtube.com/watch?v=2FOShgaQk-I
class SnackbarController(
    private val scope: CoroutineScope
){

    private var snackbarJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun getScope() = scope

    fun showSnackbar(
//        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String
    ){
        if(snackbarJob == null){
            snackbarJob = scope.launch {
//                scaffoldState.snackbarHostState.showSnackbar(
//                    message = message,
//                    actionLabel = actionLabel
//                )
                cancelActiveJob()
            }
        }
        else{
            cancelActiveJob()
            snackbarJob = scope.launch {
//                scaffoldState.snackbarHostState.showSnackbar(
//                    message = message,
//                    actionLabel = actionLabel
//                )
                cancelActiveJob()
            }
        }
    }

    private fun cancelActiveJob(){
        snackbarJob?.let { job ->
            job.cancel()
            snackbarJob = Job()
        }
    }
}