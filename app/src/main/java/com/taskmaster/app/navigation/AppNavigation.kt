package com.taskmaster.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.taskmaster.app.ui.screens.AddEditTaskScreen
import com.taskmaster.app.ui.screens.TaskListScreen
import com.taskmaster.app.ui.viewmodel.TaskViewModel

sealed class Screen(val route: String) {
    data object TaskList : Screen("task_list")
    data object AddEditTask : Screen("add_edit_task?taskId={taskId}") {
        fun createRoute(taskId: Int? = null): String =
            if (taskId != null) "add_edit_task?taskId=$taskId" else "add_edit_task?taskId=-1"
    }
}

@Composable
fun AppNavigation(viewModel: TaskViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.TaskList.route
    ) {
        composable(Screen.TaskList.route) {
            TaskListScreen(
                viewModel = viewModel,
                onAddTask = { navController.navigate(Screen.AddEditTask.createRoute()) },
                onEditTask = { taskId ->
                    navController.navigate(Screen.AddEditTask.createRoute(taskId))
                }
            )
        }

        composable(
            route = Screen.AddEditTask.route,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            AddEditTaskScreen(
                viewModel = viewModel,
                taskId = if (taskId == -1) null else taskId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
