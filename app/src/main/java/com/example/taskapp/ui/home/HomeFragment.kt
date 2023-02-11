package com.example.taskapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentHomeBinding
import com.example.taskapp.ui.home.new_task.TaskAdapter
import com.example.taskapp.ui.home.new_task.TaskEntity
import com.example.taskapp.ui.home.new_task.TaskModel
import com.example.taskapp.utils.MainApplication
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val taskAdapter = TaskAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("DEPRECATION")
        setHasOptionsMenu(true)
        initViews()
        initListeners()
        getActualTasks(MainApplication.appDatabase?.taskDao?.getAll())
    }

    private fun getActualTasks(tasks: List<TaskEntity>?) {
        lifecycleScope.launch{
            var taskModel = mutableListOf<TaskModel>()
            taskModel.clear()
            taskModel = tasks?.map {
                TaskModel(
                    id = it.id.toInt(),
                    title = it.title,
                    description = it.description,
                    uriImage = it.uriImage
                )
            } as MutableList<TaskModel>
            taskModel.let {
                taskAdapter.renew(taskModel)
            }
        }
    }

    private fun initViews() {
        binding.rvNote.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }

        taskAdapter.onDelete = {
           AlertDialog.Builder(requireContext())
               .setTitle("Внимание")
               .setMessage("Вы точно хотите удалить?")
               .setPositiveButton("Да") {_ , _ ->
                   MainApplication.appDatabase?.taskDao?.deleteById(
                       it.toLong()
                   )
                   getActualTasks(MainApplication.appDatabase?.taskDao?.getAll())
               }
               .setNegativeButton("Нет") {d, _ ->
                   d.dismiss()
               }
               .create()
               .show()
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "inflater.inflate(R.menu.burger_menu, menu)",
        "com.example.taskapp.R"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.burger_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_old -> {
                Toast.makeText(requireActivity(), "По времени", Toast.LENGTH_SHORT).show()
                getActualTasks(MainApplication.appDatabase?.taskDao?.getAllSortedByOld())
                true
            }
            R.id.sort_by_alphabet -> {
                Toast.makeText(requireContext(), "По алфавиту", Toast.LENGTH_SHORT).show()
                getActualTasks(MainApplication.appDatabase?.taskDao?.getAllSortedByAlphabet())
                true
            }
            R.id.sort_by_default -> {
                Toast.makeText(requireContext(), "По умолчанию", Toast.LENGTH_SHORT).show()
                getActualTasks(MainApplication.appDatabase?.taskDao?.getAllSortedByDefault())
                true
            }
            else -> false
        }


    }


    private fun initListeners() {

        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.newTaskFragment2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}