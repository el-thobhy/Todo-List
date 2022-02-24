package com.elthobhy.todolist.views.taskcomplete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elthobhy.todolist.R
import com.elthobhy.todolist.adapter.TaskAdapter
import com.elthobhy.todolist.databinding.FragmentTaskCompleteBinding
import com.elthobhy.todolist.model.SubTask
import com.elthobhy.todolist.model.Task
import com.elthobhy.todolist.repository.TaskRepository

class TaskCompleteFragment : Fragment() {

    private var _binding : FragmentTaskCompleteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskCompleteBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tasks = TaskRepository.getDataTask(context)

        if(tasks != null){
            for(task: Task in tasks.tasks!!){
                task.mainTask?.isComplete = true

                if(task.subTask != null){
                    for(subTask: SubTask in task.subTask!!){
                        subTask.isComplete = true
                    }
                }
            }
            showCompleteTask()
            val taskAdapter = TaskAdapter()
            taskAdapter.setData(tasks.tasks)

            binding?.rvTaskComplete?.adapter = taskAdapter
        }else{
            hideTaskComplete()
        }
    }

    private fun hideTaskComplete() {
        binding?.rvTaskComplete?.visibility = View.GONE
        binding?.layoutEmptyTaskComplete?.root?.visibility = View.VISIBLE
        binding?.fabDeleteTaskComplete?.visibility = View.GONE
    }

    private fun showCompleteTask() {
        binding?.rvTaskComplete?.visibility = View.VISIBLE
        binding?.layoutEmptyTaskComplete?.root?.visibility = View.GONE
        binding?.fabDeleteTaskComplete?.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}