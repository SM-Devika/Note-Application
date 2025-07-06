package com.example.noteapplication.uii

import androidx.compose.foundation.lazy.items
import com.example.noteapplication.uii.NoteItem

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapplication.viewmodel.NoteViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.noteapplication.data.Note
import androidx.compose.ui.Alignment


@Composable
fun NotesScreen(noteViewModel: NoteViewModel = viewModel()) {
    val notes by noteViewModel.allNotes.collectAsState(initial = emptyList())

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    var editingNoteId by remember { mutableStateOf<Int?>(null) }
    var showForm by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().align(Alignment.Center)) {

            if (showForm) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (title.isNotBlank() && description.isNotBlank()) {
                            val note = Note(
                                id = editingNoteId ?: 0,
                                title = title,
                                description = description
                            )
                            if (isEditing) {
                                noteViewModel.update(note)
                            } else {
                                noteViewModel.insert(note)
                            }
                            title = ""
                            description = ""
                            isEditing = false
                            editingNoteId = null
                            showForm = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isEditing) "Update Note" else "Add Note")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            LazyColumn {
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onDelete = { noteViewModel.delete(it) },
                        onEdit = {
                            title = it.title
                            description = it.description
                            editingNoteId = it.id
                            isEditing = true
                            showForm = true
                        }
                    )
                }
            }
        }


        FloatingActionButton(
            onClick = {
                showForm = true
                title = ""
                description = ""
                isEditing = false
                editingNoteId = null
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Add Note")
        }
    }
}
