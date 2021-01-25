package skydu.android.offlinenotes.pages.notes.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import skydu.android.offlinenotes.data.DataUtil
import skydu.android.offlinenotes.data.Notes
import skydu.android.offlinenotes.data.database.AppDatabaseHelper
import skydu.android.offlinenotes.data.database.folder.FolderEntity
import skydu.android.offlinenotes.data.database.notes.NotesEntity
import skydu.android.offlinenotes.databinding.ActivityFolderListBinding
import skydu.android.offlinenotes.databinding.ActivityNotesDetailBinding
import skydu.android.offlinenotes.pages.folder.edit.FolderEditActivity
import skydu.android.offlinenotes.pages.notes.create.NotesCreateActivity
import skydu.android.offlinenotes.pages.notes.edit.NotesEditActivity
import skydu.android.offlinenotes.util.AppExecutors

class NotesDetailActivity : AppCompatActivity() {

    var notesId: Int = -1

    val MENU_ID_EDIT = 1
    val MENU_ID_HAPUS = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNotesDetailBinding.inflate(layoutInflater)

        notesId = intent.getIntExtra("id", -1)
        title = "Notes Detail"


        AppExecutors.getInstance().run {
            diskIO().execute {
                val notes = AppDatabaseHelper.getInstance(this@NotesDetailActivity).notesDao().getById(notesId)
                mainThread().execute {
                    binding.judul.text = notes.judul
                    binding.isi.text = notes.isi
                }
            }
        }
        setContentView(binding.root)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.run {
            menu.add(0, MENU_ID_EDIT, 0, "Edit Notes")
            menu.add(0, MENU_ID_HAPUS, 0, "Hapus Notes")
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == MENU_ID_EDIT) {
            Intent(this, NotesEditActivity::class.java).run {
                putExtra("id", notesId)
                startActivity(this)
            }
        }
        if(item.itemId == MENU_ID_HAPUS) {
            AppExecutors.getInstance().diskIO().execute {
                AppDatabaseHelper.getInstance(this).notesDao().delete(
                    NotesEntity(notesId, "","",0)
                )
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}