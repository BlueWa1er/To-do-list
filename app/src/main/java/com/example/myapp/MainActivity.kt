package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import java.util.*

data class Task(
    val id: UUID = UUID.randomUUID(),
    val text: String,
    var done: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoApp()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoApp() {
    var text by remember { mutableStateOf("") }
    val todoList = remember { mutableStateListOf<Task>() }

    // Сортировка: невыполненные сверху, выполненные внизу
    val sortedList = todoList.sortedWith(compareBy({ it.done }, { it.text }))

    Column(modifier = Modifier.padding(16.dp)) {
        // Заголовок "To-Do List" с черным цветом
        Text(
            text = "To-Do List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp), // отступ снизу для заголовка
            color = Color.Black // черный цвет текста
        )

        Row {
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Введите задачу") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (text.isNotBlank()) {
                    todoList.add(Task(text = text))
                    text = ""
                }
            }) {
                Text("Добавить")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(
                items = sortedList,
                key = { it.id }
            ) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .animateItemPlacement()  // анимация перемещения при сортировке
                        .animateContentSize(),   // анимация изменения размера при удалении
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = { checked ->
                            val index = todoList.indexOfFirst { it.id == task.id }
                            if (index != -1) {
                                todoList[index] = todoList[index].copy(done = checked)
                            }
                        }
                    )
                    Text(
                        text = task.text,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { todoList.remove(task) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Удалить",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}
