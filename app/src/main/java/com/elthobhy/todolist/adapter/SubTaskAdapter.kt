package com.elthobhy.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elthobhy.todolist.R
import com.elthobhy.todolist.databinding.ItemSubTaskBinding
import com.elthobhy.todolist.db.DbSubTaskHelper
import com.elthobhy.todolist.model.SubTask

class SubTaskAdapter(private val dbSubTaskHelper: DbSubTaskHelper): RecyclerView.Adapter<SubTaskAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val itemBinding = ItemSubTaskBinding.bind(view)
        fun bind(subTask: SubTask, listener: (View) -> Unit) {
            itemBinding.tvTitleSubTask.text = subTask.title

            itemBinding.btnDoneSubTask.setOnClickListener {
                if(subTask.isComplete){
                    subTask.isComplete = false
                    val result = dbSubTaskHelper.updateSubTask(subTask)
                    if(result>0){
                        inCompleteSubTask()
                    }
                }else{
                    subTask.isComplete = true
                    val result = dbSubTaskHelper.updateSubTask(subTask)
                    if(result>0){
                        completeSubTask()
                    }
                }
            }

            itemView.setOnClickListener {
                listener(it)
            }
        }

        private fun completeSubTask() {
            itemBinding.btnDoneSubTask.setImageResource(R.drawable.ic_task_complete_black_24dp)
        }

        private fun inCompleteSubTask() {
            itemBinding.btnDoneSubTask.setImageResource(R.drawable.ic_done_task_black_24dp)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sub_task, parent, false))

    private lateinit var subTask: List<SubTask>
    private lateinit var listener: (View) -> Unit
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subTask[position], listener)
    }

    override fun getItemCount(): Int = subTask.size

    fun setData(subTasks: List<SubTask>){
        this.subTask = subTasks
        notifyDataSetChanged()
    }

    fun onClick(listener: (View) -> Unit) {
        this.listener = listener
    }
}