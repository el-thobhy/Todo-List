package com.elthobhy.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elthobhy.todolist.R
import com.elthobhy.todolist.databinding.ItemSubTaskBinding
import com.elthobhy.todolist.model.SubTask

class SubTaskAdapter: RecyclerView.Adapter<SubTaskAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val itemBinding = ItemSubTaskBinding.bind(itemView)
        fun bind(subTask: SubTask) {
            itemBinding.tvTitleSubTask.text = subTask.title

            itemBinding.btnDoneSubTask.setOnClickListener {
                if(subTask.isComplete){
                    inCompleteSubTask()
                    subTask.isComplete = false
                }else{
                    completeSubTask()
                    subTask.isComplete = true
                }
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
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subTask[position])
    }

    override fun getItemCount(): Int = subTask.size

    fun setData(subTasks: List<SubTask>){
        this.subTask = subTasks
        notifyDataSetChanged()
    }
}