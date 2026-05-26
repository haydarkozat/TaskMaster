package com.taskmaster.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.taskmaster.app.navigation.AppNavigation
import com.taskmaster.app.ui.theme.TaskMasterTheme
import com.taskmaster.app.ui.viewmodel.TaskViewModel
import com.taskmaster.app.ui.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {

    // ViewModel'i Application context'inden gelen repository ile oluşturuyoruz
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskMasterApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskMasterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}
