package com.example.studentregister.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class],version = 1, exportSchema = false)
abstract class StudentDatabase:RoomDatabase() {

//    we have to make abstract functions for all the daos here, in this project there is only one dao

    abstract fun studentDao():StudentDao

//    since the data base object need to be singleton here we create it as a companion object

    companion object{

        @Volatile
        private var INSTANCE : StudentDatabase? = null

        fun getInstance(context : Context):StudentDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance== null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        "student_data_database"
                    ).build()
                }
//                INSTANCE = instance // Assign the newly created instance to INSTANCE
                return instance
            }
        }

    }

}