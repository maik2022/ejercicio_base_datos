package com.example.explicacion_base_datos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge


import com.example.explicacion_base_datos.DAO.UserDao
import com.example.explicacion_base_datos.Database.UserDatabase
import com.example.explicacion_base_datos.Repository.UserRepository
import com.example.explicacion_base_datos.Screen.UserApp
import com.example.explicacion_base_datos.ui.theme.viewUsers


class MainActivity : ComponentActivity() {

    private lateinit var userDao:UserDao
    private lateinit var userRepository: UserRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = UserDatabase.getDatabase(applicationContext)
        userDao = db.userDao()
        userRepository = UserRepository(userDao)

        val viewUsuarios = viewUsers(userDao)

        enableEdgeToEdge()
        setContent {
            UserApp(userRepository, viewUsuarios)
        }
    }
}
