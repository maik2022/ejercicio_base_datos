package com.example.explicacion_base_datos.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.explicacion_base_datos.Model.User


//Todas las consultas que voy a hacer sobre esa tabla. o acciones sobre la base de datos.
@Dao
interface UserDao {

    //Suspend evitar que la aplica falle cuando se realizan las peticiones al realizar operaciones asincronas.

    @Insert(onConflict = OnConflictStrategy.REPLACE) //Revision de conflictos entre registros
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")   //para listar la lista.
    suspend fun getAllUsers(): List<User>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteById(userId:Int):Int

    @Query("UPDATE users SET nombre = :nombre, apellido = :apellido, edad = :edad WHERE id = :id")
    suspend fun updateUser(id: Int, nombre: String, apellido: String, edad: Int)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun buscarId(id:Int): User?

}