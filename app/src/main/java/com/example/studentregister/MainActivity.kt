package com.example.studentregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregister.db.Student
import com.example.studentregister.db.StudentDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText : EditText
    private lateinit var ageEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button


//    Defining instance of student view model

    private lateinit var viewModel: StudentViewModel
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var adapter: StudentRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.etName)
        ageEditText = findViewById(R.id.etAge)
        saveButton = findViewById(R.id.btnSave)
        clearButton = findViewById(R.id.btnClear)
        studentRecyclerView = findViewById(R.id.rvStudent)

        val dao = StudentDatabase.getInstance(application).studentDao();

        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this,factory).get(StudentViewModel::class.java)

        initRecyclerView()
        saveButton.setOnClickListener{
            saveStudentData()
            clearInputs()
        }
        clearButton.setOnClickListener {
            clearInputs()
        }


    }


    private fun saveStudentData(){
        viewModel.insertStudent(
            Student(
                0,
                nameEditText.text.toString(),
                ageEditText.text.toString().toInt()
            )
        )
    }

     private fun clearInputs(){
         nameEditText.setText("")
         ageEditText.setText("")
     }

    private fun initRecyclerView(){
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerViewAdapter()
        studentRecyclerView.adapter = adapter
        displayStudentsList()
    }
    private fun displayStudentsList(){
        viewModel.students.observe(
            this,
        ) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }
}