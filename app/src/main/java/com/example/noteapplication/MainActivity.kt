package com.example.noteapplication

import com.example.noteapplication.uii.NotesAppTheme


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapplication.uii.NotesScreen
import com.example.noteapplication.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesAppTheme {
                val noteViewModel: NoteViewModel = viewModel()
                NotesScreen()
            }
        }
    }
}
