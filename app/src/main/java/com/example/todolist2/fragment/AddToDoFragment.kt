package com.example.todolist2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todolist2.R
import com.example.todolist2.databinding.FragmentAddToDoBinding
import com.example.todolist2.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText


class AddToDoFragment : DialogFragment() {

    private lateinit var binding: FragmentAddToDoBinding
    private lateinit var listeners: DialogNextBtnClickListeners
    private var toDoData: ToDoData? = null

    fun setListener(listeners: DialogNextBtnClickListeners){
        this.listeners = listeners
    }

    companion object{
        const val Tag = "AddToDoFragment"

        @JvmStatic
        fun newInstance(taskId: String, task:String ) = AddToDoFragment().apply{
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddToDoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null){
            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString())

            binding.task.setText(toDoData?.task)
        }
        registerEvents()
    }

    private fun registerEvents(){
        binding.sumbitBtn.setOnClickListener {
            val toDoTask = binding.task.text.toString().trim()
            if(toDoTask.isNotEmpty()){
                if(toDoData == null){
                    listeners.onSaveTask(toDoTask, binding.task)
                }else {
                    toDoData?.task = toDoTask
                    listeners.onUpdateTask(toDoData!! , binding.task)
                }
            }else{
                Toast.makeText(context, "Please type some task", Toast.LENGTH_LONG).show()
            }
        }
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListeners{
        fun onSaveTask(toDo: String, task: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, task: TextInputEditText)
    }


}