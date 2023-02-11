package com.example.taskapp.ui.home.new_task

import androidx.room.*

data class TaskModel(
    val id: Int,
    val title: String,
    val description: String,
    val uriImage: String? = null
)

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "desc")
    val description: String,
    @ColumnInfo(name = "picture_uri")
    val uriImage: String? = null
)

@Dao
interface TaskDao {

    @Insert
    fun insert(tasks: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getAll(): List<TaskEntity>

    @Query("DELETE FROM tasks WHERE id = :taskId")
    fun deleteById(taskId : Long)

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllSortedByOld(): List<TaskEntity>

    @Query("SELECT * FROM tasks ORDER BY title ASC")
    fun getAllSortedByAlphabet(): List<TaskEntity>

    @Query("SELECT * FROM tasks")
    fun getAllSortedByDefault(): List<TaskEntity>


}
