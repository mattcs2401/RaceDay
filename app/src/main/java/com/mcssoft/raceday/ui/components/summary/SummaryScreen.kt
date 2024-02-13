package com.mcssoft.raceday.ui.components.summary

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.ui.components.dialog.CommonDialog
import com.mcssoft.raceday.ui.components.dialog.LoadingDialog
import com.mcssoft.raceday.ui.components.navigation.PagerItems
import com.mcssoft.raceday.ui.components.navigation.Screens
import com.mcssoft.raceday.ui.components.summary.SummaryState.Status
import com.mcssoft.raceday.ui.components.summary.components.SummaryItem
import com.mcssoft.raceday.ui.theme.components.card.topappbar.lightSummaryTopAppBarColours
import com.mcssoft.raceday.ui.theme.padding64dp
import com.mcssoft.raceday.ui.theme.padding8dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Reference: https://www.geeksforgeeks.org/tab-layout-in-android-using-jetpack-compose/

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SummaryScreen(
    state: SummaryState,
    navController: NavController,
    onEvent: (SummaryEvent) -> Unit
) {
    val showRemoveDialog = remember { mutableStateOf(false) }

    val summaryId = remember { mutableLongStateOf(0L) }

    val success = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val pagerItems = listOf(
        PagerItems.Current,
        PagerItems.Previous
    )
    val pagerState = rememberPagerState(pageCount = { pagerItems.size })

    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(content = {
                        Text(
                            stringResource(id = R.string.label_summary),
                            modifier = Modifier.weight(weight = 2f),
                        )
                    })
                },
                colors = lightSummaryTopAppBarColours,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            backNavigate(navController, state)
                        }
                    ) {
                        Icon(
                            painterResource( id = R.drawable.ic_arrow_back_24 ),
                            stringResource(id = R.string.label_runner)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(SummaryEvent.Refresh)
                        }
                    ){
                        Icon(
                            painterResource(id = R.drawable.ic_refresh_24),
                            stringResource(id = R.string.lbl_icon_refresh)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding64dp)
        ) {
            PagerTabRow(
                selectedIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                pagerItems = pagerItems,
                scope = scope,
                pagerState = pagerState
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                when(page) {
                    0 -> {
                        TabContentCurrent(
                            success = success,
                            state.cSummaries,
                            navController = navController,
                            showRemoveDialog = showRemoveDialog,
                            summaryId = summaryId
                        )
                    }
                    1 -> {
                        TabContentPrevious(
                            success = success,
                            state.pSummaries,
                            navController = navController,
                            showRemoveDialog = showRemoveDialog,
                            summaryId = summaryId

                        )
                    }
                }
            }
        } // end column scope.
    } // end scaffold scope.
    if (showRemoveDialog.value) {
        ShowRemoveDialog(
            show = showRemoveDialog,
            summaryId = summaryId.longValue,
            onEvent = onEvent
        )
    }
    when (state.status) {
        is Status.Initialise -> {}
        is Status.Loading -> {
            LoadingDialog(
                titleText = stringResource(id = R.string.dlg_loading_summaries),
                msgText = stringResource(id = R.string.dlg_loading_msg),
                onDismiss = {}
            )
        }
        is Status.Failure -> {}
        is Status.Success -> {
            success.value = true
        }
    }
}

@Composable
fun TabContentCurrent(
    success: MutableState<Boolean>,
    summaries: List<Summary>,
    navController: NavController,
    showRemoveDialog: MutableState<Boolean>,
    summaryId: MutableState<Long>
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(success.value) {
            if (summaries.isEmpty()) {
                NoSummaries()
            } else {
                ShowCurrentSummaries(
                    summaries,
                    navController,
                    showRemoveDialog,
                    summaryId
                )
            }
        }
    }
}

@Composable
fun TabContentPrevious(
    success: MutableState<Boolean>,
    summaries: List<Summary>,
    navController: NavController,
    showRemoveDialog: MutableState<Boolean>,
    summaryId: MutableState<Long>
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(success.value) {
            if (summaries.isEmpty()) {
                NoSummaries()
            } else {
                ShowPreviousSummaries(
                    summaries,
                    navController,
                    showRemoveDialog,
                    summaryId
                )
            }
        }
    }
}

@Composable
private fun ShowCurrentSummaries(
    summaries: List<Summary>,
    navController: NavController,
    showRemoveDialog: MutableState<Boolean>,
    summaryId: MutableState<Long>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding8dp)
    ) {
        items(
            items = summaries,
            key = { it.id }
        ) { summary ->
            SummaryItem(
                summary = summary,
                onItemClick = {
                    navController.navigate(
                        Screens.RunnerScreen.route + "runnerId=${summary.runnerId}"
                    )
                },
                onItemLongClick = {
                    showRemoveDialog.value = true
                    summaryId.value = it.id
                }
            )
        }
    }
}

@Composable
private fun ShowPreviousSummaries(
    summaries: List<Summary>,
    navController: NavController,
    showRemoveDialog: MutableState<Boolean>,
    summaryId: MutableState<Long>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding8dp)
    ) {
        items(
            items = summaries,
            key = { it.id }
        ) { summary ->
            SummaryItem(
                summary = summary,
                onItemClick = {
                    navController.navigate(
                        Screens.RunnerScreen.route + "runnerId=${summary.runnerId}"
                    )
                },
                onItemLongClick = {
                    showRemoveDialog.value = true
                    summaryId.value = it.id
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PagerTabRow(
    selectedIndex: State<Int>,
    modifier: Modifier,
    pagerItems: List<PagerItems>,
    scope: CoroutineScope,
    pagerState: PagerState
) {
    TabRow(
        selectedTabIndex = selectedIndex.value,
        modifier.fillMaxWidth()
    ) {
        pagerItems.forEachIndexed { index, currentTab ->
            Tab(
                selected = selectedIndex.value == index,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.outline,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(currentTab.ordinal)
                    }
                },
                text = { Text(text = currentTab.title) },
//                        icon = {
//                            Icon(
//                                imageVector = if (selectedTabIndex.value == index)
//                                    currentTab.selectedIcon else currentTab.unselectedIcon,
//                                contentDescription = "Tab Icon"
//                            )
//                        }
            )
        }
    }
}
@Composable
private fun NoSummaries() {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                top = padding64dp
            )
    ) {
        Text(
            stringResource(id = R.string.nothing_to_show),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private fun backNavigate(
    navController: NavController,
    state: SummaryState
) {
    navController.navigate(
        Screens.MeetingsScreen.route + "fromApi=${state.fromApi}"
    ) {
        popUpTo(route = Screens.MeetingsScreen.route) {
            inclusive = true
        }
    }
}

@Composable
private fun ShowRemoveDialog(
    show: MutableState<Boolean>,
    summaryId: Long,
    onEvent: (SummaryEvent) -> Unit
) {
    CommonDialog(
        icon = R.drawable.ic_summary_48,
        dialogTitle = stringResource(id = R.string.dlg_remove_summary_title),
        dialogText = stringResource(id = R.string.dlg_remove_summary_text),
        confirmButtonText = stringResource(id = R.string.lbl_btn_ok),
        dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
        onConfirmClicked = {
            show.value = !show.value
            onEvent(SummaryEvent.Removal(summaryId))
        },
        onDismissClicked = {
            show.value = !show.value
        }
    )
}
