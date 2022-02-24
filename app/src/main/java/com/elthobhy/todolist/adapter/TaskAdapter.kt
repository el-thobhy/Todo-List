package com.elthobhy.todolist.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elthobhy.todolist.R
import com.elthobhy.todolist.databinding.ItemTaskBinding
import com.elthobhy.todolist.db.DbSubTaskHelper
import com.elthobhy.todolist.db.DbTaskHelper
import com.elthobhy.todolist.model.SubTask
import com.elthobhy.todolist.model.Task

class TaskAdapter(
    private val dbTaskHelper: DbTaskHelper,
    private val dbSubTaskHelper: DbSubTaskHelper
): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val itemBinding = ItemTaskBinding.bind(view)
        fun bind(
            task: Task,
            listener: ((Task) -> Unit)?,
            dbTaskHelper: DbTaskHelper,
            dbSubTaskHelper: DbSubTaskHelper
        ) {
            itemBinding.tvTitleTask.text = task.mainTask?.title
            val subTaskAdapter = SubTaskAdapter(dbSubTaskHelper)

           if(task.mainTask?.isComplete!!){
               completeTask()
           }else{
               inCompleteTask()
           }
            if(task.mainTask!!.date != null && task.mainTask!!.date!!.isNotEmpty()){
                showDateTask()
                itemBinding.tvDateTask.text = task.mainTask!!.date
            }else{
                hideDateTask()
            }

            if(task.subTask != null){
                showSubTasks()
                subTaskAdapter.setData(task.subTask!!)

                itemBinding.rvSubTask.adapter = subTaskAdapter

            }else{
                hideSubTasks()
            }
            itemBinding.btnDoneTask.setOnClickListener {
                if(task.mainTask?.isComplete!!){
                    task.mainTask!!.isComplete = false
                    val result = dbTaskHelper.updateTask(task.mainTask)
                    if(result>0){
                        inCompleteTask()
                        Handler().postDelayed({
                              deleteDataTask(adapterPosition)
                        }, 1200)
                        if(task.subTask != null){
                            var isSuccess = false
                            for(subTask: SubTask in task.subTask!!){
                                subTask.isComplete = true
                                val resultSubTask = dbSubTaskHelper.updateSubTask(subTask)
                                if(resultSubTask>0){
                                    isSuccess = true
                                }
                            }
                            if(isSuccess){
                                subTaskAdapter.setData(task.subTask!!)
                            }
                        }
                    }
                }else{
                    task.mainTask!!.isComplete = true
                    val result = dbTaskHelper.updateTask(task.mainTask)
                    if(result>0){
                        completeTask()
                        Handler().postDelayed({
                              deleteDataTask(adapterPosition)
                        },1200)
                        if(task.subTask != null){
                            var isSuccess = false
                            for(subTask: SubTask in task.subTask!!){
                                subTask.isComplete = true
                                val resultSubTask = dbSubTaskHelper.updateSubTask(subTask)
                                if(result>0){
                                    isSuccess =true
                                }
                            }
                            if(isSuccess){
                                subTaskAdapter.setData(task.subTask!!)
                            }
                        }
                    }
                }
            }

            itemView.setOnClickListener {
                if (listener != null) {
                    listener(task)
                }
            }

            subTaskAdapter.onClick{
                if (listener != null) {
                    listener(task)
                }
            }
        }



        private fun completeTask() {
            itemBinding.btnDoneTask.setImageResource(R.drawable.ic_task_complete_black_24dp)
        }

        private fun inCompleteTask() {
            itemBinding.btnDoneTask.setImageResource(R.drawable.ic_done_task_black_24dp)
        }

        private fun hideSubTasks() {
            itemBinding.lineTask.visibility = View.GONE
            itemBinding.rvSubTask.visibility = View.GONE
        }

        private fun showSubTasks() {
            itemBinding.lineTask.visibility = View.VISIBLE
            itemBinding.rvSubTask.visibility = View.VISIBLE
        }

        private fun hideDateTask() {
            itemBinding.containerDateTask.visibility = View.GONE
        }

        private fun showDateTask() {
            itemBinding.containerDateTask.visibility = View.VISIBLE
        }

    }

    private var tasks = mutableListOf<Task>()
    private var listener:( (Task) ->Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position], listener, dbTaskHelper, dbSubTaskHelper)
    }

    override fun getItemCount(): Int = tasks.size

    fun setData(tasks: List<Task>){
        this.tasks = tasks as MutableList<Task>
        notifyDataSetChanged()
    }

    fun onCLick(listener: (Task) -> Unit){
        this.listener = listener
    }

    private fun deleteDataTask(adapterPosition: Int) {
        tasks.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
        notifyItemRangeChanged(adapterPosition, tasks.size)
    }

    fun deleteAllDataTask(){
        tasks.removeAll(tasks)
        notifyDataSetChanged()

    }
}