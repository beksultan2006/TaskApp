package com.example.taskapp.ui.home.new_task

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.taskapp.databinding.ItemTaskBinding
import com.example.taskapp.ui.home.HomeFragment

class TaskAdapter(private var tasks: MutableList<TaskModel>) :
    Adapter<TaskAdapter.TaskViewHolder>() {

    var onDelete: ((Int) -> Unit)? = null

    fun add(task: TaskModel) {
        tasks.add(0, task)
        notifyItemChanged(0)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun renew(taskModel: List<TaskModel>) {
        tasks.clear()
        tasks.addAll(taskModel)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }



    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        ViewHolder(binding.root) {
        fun bind(task: TaskModel) {
            binding.tvTitle.text = task.title
            binding.tvDesc.text = task.description
            if (task.uriImage != null) {
//                binding.itemPicture.setImageURI(Uri.parse(task.uriImage))
                Glide.with(binding.root).load(task.uriImage).into(binding.itemPicture)
                binding.itemPicture.visibility = View.VISIBLE
            }

            binding.root.setOnLongClickListener {
                onDelete?.invoke(task.id)
                true
            }
        }

    }

}