package com.example.studentregister.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {

//    since we are not executing these in the main thread so we added suspend infront of the function
    @Insert
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

//    these function will automatically executed in the background thread by the room library so no need suspend keyword here
    @Query("SELECT * FROM student_data_table")
    fun getAllStudents():LiveData<List<Student>>


}