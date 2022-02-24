package com.elthobhy.todolist.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elthobhy.todolist.R
import com.elthobhy.todolist.adapter.TaskAdapter
import com.elthobhy.todolist.databinding.FragmentHomeBinding
import com.elthobhy.todolist.db.DbSubTaskHelper
import com.elthobhy.todolist.db.DbTaskHelper
import com.elthobhy.todolist.repository.TaskRepository

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() =_binding
    private lateinit var dbTaskHelper: DbTaskHelper
    private lateinit var dbSubTaskHelper: DbSubTaskHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun onResume() {
        super.onResume()
        getDataTask()
    }

    private fun getDataTask() {
        val tasks = TaskRepository.getDataTaskFromDatabase(dbTaskHelper,dbSubTaskHelper)
        if(tasks != null){
            showTasks()
            val taskAdapter = TaskAdapter()
            taskAdapter.setData(tasks)

            binding?.rvTask?.adapter = taskAdapter
        }else{
            hideTasks()
        }
    }

    private fun setup() {
        dbTaskHelper = DbTaskHelper.getInstance(context)
        dbSubTaskHelper = DbSubTaskHelper.getInstance(context)
    }

    private fun hideTasks() {
        binding?.rvTask?.visibility = View.GONE
        binding?.layoutEmptyTask?.root?.visibility = View.VISIBLE
    }

    private fun showTasks() {

        binding?.rvTask?.visibility = View.VISIBLE
        binding?.layoutEmptyTask?.root?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}