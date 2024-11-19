package com.meproject.newsstream.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.meproject.newsstream.presentation.ui.auth.login.AuthViewModel
import com.meproject.newsstream.presentation.ui.auth.login.Login
import com.meproject.newsstream.presentation.ui.auth.login.loginScreenDestination
import com.meproject.newsstream.presentation.ui.auth.preference.Preference
import com.meproject.newsstream.presentation.ui.auth.preference.preferenceScreenDestination
import com.meproject.newsstream.presentation.ui.auth.signup.Signup
import com.meproject.newsstream.presentation.ui.auth.signup.signupScreenDestination
import com.meproject.newsstream.presentation.ui.theme.NewsStreamTheme
import kotlinx.serialization.Serializable

@Composable
fun NewsStreamApp() {
    NewsStreamTheme {
        val authViewModel: AuthViewModel = hiltViewModel()
        val isLoggedIn = authViewModel.isLoggedIn.value
        val navController = rememberNavController()
        val startDestination: NavigationDestination = if (isLoggedIn == true) Main else AuthGraph
        NavHost(navController = navController, startDestination = startDestination) {
            navigation<AuthGraph>(startDestination = Login) {
                loginScreenDestination(
                    onNavigationToHome = { navController.navigate(Main) },
                    onNavigationToSignup = { navController.navigate(Signup) },
                    onNavigationToForgotPassword = { }
                )
                signupScreenDestination(
                    onNavigationToLogin = { navController.navigate(Login) }
                ) {
                    navController.navigate(Preference) {
                        popUpTo(Preference)
                    }
                }
                preferenceScreenDestination { navController.navigate(Main) }
            }
            mainScreenDestination()
        }
    }
}

@Serializable
object AuthGraph : NavigationDestination