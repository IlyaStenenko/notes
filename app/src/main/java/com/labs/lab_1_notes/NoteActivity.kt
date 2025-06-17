package com.labs.lab_1_notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class NoteActivity : ComponentActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private var noteIndex: Int = -1
    private var requestCode: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        titleEditText = findViewById(R.id.editTitle)
        contentEditText = findViewById(R.id.editContent)
        val saveButton: Button = findViewById(R.id.saveButton)

        val intent = intent
        titleEditText.setText(intent.getStringExtra("title"))
        contentEditText.setText(intent.getStringExtra("content"))
        noteIndex = intent.getIntExtra("index", -1)
        requestCode = intent.getIntExtra("requestCode", -1)

        saveButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("title", titleEditText.text.toString())
                putExtra("content", contentEditText.text.toString())
                putExtra("index", noteIndex)
                putExtra("requestCode", requestCode)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}

