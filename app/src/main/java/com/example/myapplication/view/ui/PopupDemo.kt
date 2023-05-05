package com.example.myapplication.view.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.example.myapplication.viewmodel.ListViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PopupDemo(messageAlert:String,viewModel: ListViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
           var popup by remember { mutableStateOf(false) }
            if (!popup) {
                Popup(
                    alignment = Alignment.Center,
                    offset = IntOffset(0, 30),
                    onDismissRequest = { popup = false },
                    properties = PopupProperties(
                        focusable = true,
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false,
                        securePolicy = SecureFlagPolicy.SecureOn,
                        excludeFromSystemGesture = false,
                        clippingEnabled = false,
                        usePlatformDefaultWidth = false
                    )
                ) {
                    Surface(
                        border = BorderStroke(6.dp, MaterialTheme.colors.primary),
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xCCEEEEEE),
                    ) {
                        Column(
                            modifier = Modifier.padding(30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = messageAlert)
                            Spacer(modifier = Modifier.height(20.dp))
                            TextButton(onClick = { popup = true
                                viewModel.getData()}) {
                                Text(text = "Ok")

                            }
                        }
                    }
                }
        }
    }
}