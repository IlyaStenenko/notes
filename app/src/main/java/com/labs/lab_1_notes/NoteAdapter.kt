package com.labs.lab_1_notes

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher

class NoteAdapter(
    private val context: Context,
    private val notes: MutableList<Note>,
    private val launcher: ActivityResultLauncher<Intent>,
    private val onDelete: (Int) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = notes.size

    override fun getItem(position: Int): Any = notes[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)

        val titleText: TextView = view.findViewById(R.id.noteTitle)
        val editButton: ImageButton = view.findViewById(R.id.editButton)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)

        titleText.text = notes[position].title

        titleText.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(notes[position].title)
                .setMessage(notes[position].content)
                .setPositiveButton("OK", null)
                .show()
        }

        editButton.setOnClickListener {
            val intent = Intent(context, NoteActivity::class.java).apply {
                putExtra("title", notes[position].title)
                putExtra("content", notes[position].content)
                putExtra("index", position)
                putExtra("requestCode", 2)
            }
            launcher.launch(intent)
        }

        deleteButton.setOnClickListener {
            onDelete(position)
        }

        return view
    }

}
