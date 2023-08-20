package com.example.studentregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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


//    for update or delete a student

    private lateinit var selectedStudent: Student
    private var isListItemClicked = false



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
            if (isListItemClicked){
                updateStudentData()
                clearInputs()

            }else {

                saveStudentData()
                clearInputs()
            }
        }
        clearButton.setOnClickListener {
            if (isListItemClicked){
                deleteStudentData()
                clearInputs()
            }
            else{
                clearInputs()
            }


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
        adapter = StudentRecyclerViewAdapter {
                selectedItem: Student -> listItemClicked(selectedItem)
        }
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


    private fun listItemClicked(student: Student){
//        Toast.makeText(this,
//            "Student name is ${student.name}"
//        , Toast.LENGTH_SHORT
//            ).show()

        selectedStudent = student
        saveButton.text = "Update"
        clearButton.text = "Delete"
        isListItemClicked = true

        nameEditText.setText(selectedStudent.name)
        ageEditText.setText(selectedStudent.age.toString())

   }
    private fun updateStudentData(){
        viewModel.updateStudent(
            Student(
                selectedStudent.id,
                nameEditText.text.toString(),
                ageEditText.text.toString().toInt()

            )
        )
//        selectedStudent = null
        saveButton.text = "Save"
        clearButton.text = "Clear"
        isListItemClicked = false
    }

    private fun deleteStudentData(){
        viewModel.deleteStudent(
            Student(
                selectedStudent.id,
                selectedStudent.name,
                selectedStudent.age
            )
        )
//        selectedStudent = null
        saveButton.text = "Save"
        clearButton.text = "Clear"
        isListItemClicked = false
    }
}