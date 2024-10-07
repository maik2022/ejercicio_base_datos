package com.example.explicacion_base_datos.Repository

import com.example.explicacion_base_datos.DAO.UserDao
import com.example.explicacion_base_datos.Model.User

//Buena practica es sobre escribir los metodos del userdao
class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: User){
        userDao.insert(user)
    }

    suspend fun getAllUsers(): List<User>{
        return userDao.getAllUsers()
    }

    suspend fun deleteById(userId: Int): Int{
        return userDao.deleteById(userId)
    }

    suspend fun updateUser(id: Int, nombre: String, apellido: String, edad: Int){
        return userDao.updateUser(id, nombre, apellido, edad)
    }

    suspend fun buscarId(id:Int): User?{
        return userDao.buscarId(id)
    }


}