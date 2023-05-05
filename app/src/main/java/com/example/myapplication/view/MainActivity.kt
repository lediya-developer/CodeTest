package com.example.myapplication.view
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.utility.ResultType
import com.example.myapplication.viewmodel.ListViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)

        }
    }
}
