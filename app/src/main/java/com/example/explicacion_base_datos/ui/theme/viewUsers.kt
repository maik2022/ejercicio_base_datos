package com.example.explicacion_base_datos.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explicacion_base_datos.DAO.UserDao
import com.example.explicacion_base_datos.Model.User
import kotlinx.coroutines.launch

class viewUsers(val userDao: UserDao): ViewModel(){

    var listUsers by mutableStateOf(emptyList<User>())

    fun obtenerUsuario(){
        viewModelScope.launch {
            listUsers = userDao.getAllUsers()
        }
    }

}