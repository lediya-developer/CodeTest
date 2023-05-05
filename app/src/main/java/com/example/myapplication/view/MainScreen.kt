package com.example.myapplication.view


import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.data.BandList
import com.example.myapplication.model.data.Festival
import com.example.myapplication.model.data.RecordLabel
import com.example.myapplication.viewmodel.ListViewModel
import com.example.myapplication.R
import com.example.myapplication.view.ui.PopupDemo
import com.example.myapplication.view.ui.ProgressLoader
import com.example.myapplication.view.ui.theme.MyApplicationTheme



@Composable
fun MainScreen(viewModel: ListViewModel) {
    val itemIds by viewModel.itemIds.collectAsState()
    val errorCode = viewModel.errorCode.collectAsState()

    Scaffold(
        topBar = { TopBar() }
    ) { padding ->
        // We need to pass scaffold's inner padding to the content
        if( errorCode.value ==3){
            MyApplicationTheme() {
                PopupDemo("Too many Request",viewModel)
            }
        } else if(errorCode.value == 2){
        MyApplicationTheme() {
            PopupDemo("Please try again",viewModel)
        }
    }
        else if(errorCode.value==0){
            ProgressLoader()
        }
        else if(errorCode.value==1){
            LazyColumn(modifier = Modifier.padding(padding)) {
                itemsIndexed(viewModel.items.value) { index, item ->
                    ExpandableContainerView(
                        itemModel = item,
                        onClickItem = { viewModel.onItemClicked(index) },
                        expanded = itemIds.contains(index)
                    )
                }
            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val viewModel = ListViewModel()
    MainScreen(viewModel = viewModel)
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor =colorResource(id = R.color.purple_700),
        contentColor = Color.White
    )
}

@Preview(showBackground = false)
@Composable
fun TopBarPreview() {
    TopBar()
}
@Composable
fun ExpandableContainerView(itemModel: RecordLabel, onClickItem: () -> Unit,
                            expanded: Boolean) {
    Box(
        modifier = Modifier.background(colorResource(id = R.color.purple_200))
    ) {
        Column {
            HeaderView(itemModel.name, onClickItem = onClickItem)
            itemModel.bands?.let { ExpandableView(it, onChildClickItem=onClickItem ,expanded) }
        }
    }
}

@Composable
fun HeaderView(recordLabelText: String, onClickItem: () -> Unit) {
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.purple_500))
            .clickable(
                indication = null, // Removes the ripple effect on tap
                interactionSource = remember { MutableInteractionSource() }, // Removes the ripple effect on tap
                onClick = onClickItem
            )
    ) {
        if(recordLabelText.contains(" . ")){
            Text(
                text = " ",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }else{
            Text(
                text = recordLabelText,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

    }
}
@Composable
fun ChildHeaderView(recordLabelText: String, onClickItem: () -> Unit) {
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.purple_200))
            .clickable(
                indication = null, // Removes the ripple effect on tap
                interactionSource = remember { MutableInteractionSource() }, // Removes the ripple effect on tap
                onClick = onClickItem
            )
    ) {
        Text(
            text = recordLabelText,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
        )

    }
}
@Preview(showBackground = true)
@Composable
fun HeaderViewPreview() {
    HeaderView("Question") {}
}

@Composable
fun ExpandableView(bands: List<BandList>, onChildClickItem: () -> Unit,isExpanded: Boolean) {
    // Opening Animation
    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }

    // Closing Animation
    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition
    ) {
        Box(modifier = Modifier
            .clickable(
                indication = null, // Removes the ripple effect on tap
                interactionSource = remember { MutableInteractionSource() }, // Removes the ripple effect on tap
                onClick = onChildClickItem
            )
            .padding(8.dp)) {
            Column {
                bands.forEach { it ->
                    ChildHeaderView(it.bandName, onClickItem = onChildClickItem)
                    it.festivals?.let { NestedExpandableView(it, isExpanded = true) }
                }

            }


        }


    }
}
@Composable
fun NestedExpandableView(festivals: List<Festival>, isExpanded: Boolean) {
    // Opening Animation
    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }

    // Closing Animation
    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition
    ) {
        festivals.forEach {
            Box(modifier = Modifier.padding(8.dp)) {
                Column {
                it.name?.let { it1 ->
                    Text(
                        text = it1,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                    }
                }
            }
        }
    }
}
