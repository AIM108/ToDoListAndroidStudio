package com.example.alonsomontelongotodolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTask= mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object: TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //1.Remove the item from the list
                listOfTask.removeAt(position)
                //2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()
                saveItems()

            }

        }

        loadItems()
        //Look up recyclerView in layout
        val recyclerView =findViewById<RecyclerView>(R.id.recyclerView)
        //Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTask, onLongClickListener)
        recyclerView.adapter =adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        val inputTextField = findViewById<EditText>(R.id.taskText)



        findViewById<Button>(R.id.addTaskButton).setOnClickListener{
            val userInputTask = inputTextField.text.toString()

            listOfTask.add(userInputTask)

            adapter.notifyItemInserted(listOfTask.size-1)

            inputTextField.setText("")
            saveItems()

        }
    }



    //Save thea data that the user has inputted
    //Save data by writing and reading from a file

    //Create a method to get the file we need
    fun getDataFile() : File {
        //Every line is going to represent a specific task in our list of task


        return File(filesDir, "data.txt")

    }

    //Load the items by reading every line in the data file
    fun loadItems(){

        try {
            listOfTask = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch(ioException: IOException){
            ioException.printStackTrace()
        }


    }


    //Save items by writing them into our data file
    fun saveItems() {
     try {
         org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTask)
     }catch(ioException: IOException){
         ioException.printStackTrace()
     }




     }

}