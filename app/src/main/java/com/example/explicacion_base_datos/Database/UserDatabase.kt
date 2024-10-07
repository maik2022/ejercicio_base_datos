package com.example.explicacion_base_datos.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.explicacion_base_datos.DAO.UserDao
import com.example.explicacion_base_datos.Model.User


// abstract para evitar crear nuevas instancias de la db t room gestiona la creacion de la bd automaticamente
//compain object se usa para definir miembros estaticos en kotlin
//volatile permite que cualquier hilo que acceda a la variable tenga la version mas actualizada

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}