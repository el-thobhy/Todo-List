package com.elthobhy.todolist.adapter

import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.elthobhy.todolist.R
import com.elthobhy.todolist.databinding.ItemAddSubTaskBinding
import com.elthobhy.todolist.model.SubTask
import kotlin.math.sign

class AddSubTaskAdapter: RecyclerView.Adapter<AddSubTaskAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val itemBinding= ItemAddSubTaskBinding.bind(view)
        fun bind(subTask: SubTask) {
            if(subTask.title != null){
                itemBinding.etTitleSubTask.setText(subTask.title)
            }
            if(subTask.isComplete){
                completeTask()
            }else{
                inCompleteTask()
            }

            itemBinding.btnRemoveSubTask.setOnClickListener {
                deleteTask(adapterPosition)
            }

            itemBinding.etTitleSubTask.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    subTask.title = p0.toString()
                    update(subTask, adapterPosition)
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
        }

        private fun inCompleteTask() {
            itemBinding.btnCompleteSubTask.setImageResource(R.drawable.ic_done_task_black_24dp)
            itemBinding.etTitleSubTask.paintFlags = Paint.ANTI_ALIAS_FLAG
        }

        private fun completeTask() {
            itemBinding.btnCompleteSubTask.setImageResource(R.drawable.ic_task_complete_black_24dp)
            itemBinding.etTitleSubTask.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_add_sub_task, parent, false))


    private var listAddSubTask = mutableListOf<SubTask>()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listAddSubTask[position])
    }

    override fun getItemCount(): Int = listAddSubTask.size

    fun deleteTask(adapterPosition: Int) {
        listAddSubTask.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
        notifyItemRangeChanged(adapterPosition,listAddSubTask.size)
    }
    fun update(subTask: SubTask, adapterPosition: Int) {
        listAddSubTask[adapterPosition] = subTask
    }
    fun addTask(subTask: SubTask){
        listAddSubTask.add(subTask)
        notifyItemInserted(listAddSubTask.size - 1)
    }
}