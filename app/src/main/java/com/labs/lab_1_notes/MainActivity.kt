package com.labs.lab_1_notes
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: NoteAdapter
    private val notes = mutableListOf<Note>()

    private val noteActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult
            val title = data.getStringExtra("title") ?: return@registerForActivityResult
            val content = data.getStringExtra("content") ?: return@registerForActivityResult
            val requestCode = data.getIntExtra("requestCode", -1)
            val index = data.getIntExtra("index", -1)

            when (requestCode) {
                1 -> {
                    notes.add(Note(title, content))
                }
                2 -> {
                    if (index != -1) {
                        notes[index].title = title
                        notes[index].content = content
                    }
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        val addButton: ImageButton = findViewById(R.id.addButton)

        adapter = NoteAdapter(this, notes, noteActivityLauncher) { position ->
            notes.removeAt(position)
            adapter.notifyDataSetChanged()
        }

        listView.adapter = adapter

        addButton.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java).apply {
                putExtra("requestCode", 1)
            }
            noteActivityLauncher.launch(intent)
        }
    }
}


