package skydu.android.offlinenotes.pages.folder.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import skydu.android.offlinenotes.R
import skydu.android.offlinenotes.data.DataUtil
import skydu.android.offlinenotes.data.Notes
import skydu.android.offlinenotes.data.database.AppDatabaseHelper
import skydu.android.offlinenotes.data.database.folder.FolderEntity
import skydu.android.offlinenotes.databinding.ActivityFolderListBinding
import skydu.android.offlinenotes.pages.folder.edit.FolderEditActivity
import skydu.android.offlinenotes.pages.notes.create.NotesCreateActivity
import skydu.android.offlinenotes.pages.notes.detail.NotesDetailActivity
import skydu.android.offlinenotes.util.AppExecutors

class FolderDetailActivity : AppCompatActivity() {
    lateinit var folderDetailAdapter: FolderDetailAdapter

    val MENU_ID_CREATE_NOTES = 0
    val MENU_ID_EDIT = 1
    val MENU_ID_HAPUS = 2

    var folderId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFolderListBinding.inflate(layoutInflater)

        folderId = intent.getIntExtra("id", -1)


        val onItemClick = { notes: Notes ->
            Intent(this, NotesDetailActivity::class.java).run {
                putExtra("id", notes.id)
                startActivity(this)
            }
        }

        folderDetailAdapter = FolderDetailAdapter(onItemClick)

        val padding = resources.getDimensionPixelSize(R.dimen.padding) / 4
        binding.recyclerView.run {
            clipToPadding = false
            setPadding(padding, padding, padding, padding)
            addItemDecoration(
                DividerItemDecoration(
                    this@FolderDetailActivity,
                    DividerItemDecoration.VERTICAL
                )
            )

            layoutManager =
                LinearLayoutManager(this@FolderDetailActivity, RecyclerView.VERTICAL, false)
            adapter = folderDetailAdapter
        }
        setContentView(binding.recyclerView)
    }


    override fun onResume() {
        super.onResume()
        AppExecutors.getInstance().run {
            diskIO().execute {
                val database = AppDatabaseHelper.getInstance(this@FolderDetailActivity)
                database.folderDao().getById(folderId).run {
                    mainThread().execute {
                        title = this.judul
                    }
                }
                database.notesDao().getAllByFolderId(folderId).run {
                    mainThread().execute {
                        folderDetailAdapter.submitData(
                            DataUtil.convertFromNotesEntityList(this)
                        )
                    }
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.run {
            menu.add(0, MENU_ID_CREATE_NOTES, 0, "Create Notes")
            menu.add(0, MENU_ID_EDIT, 0, "Edit Folder")
            menu.add(0, MENU_ID_HAPUS, 0, "Hapus Folder")
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == MENU_ID_EDIT) {
            Intent(this, FolderEditActivity::class.java).run {
                putExtra("id", folderId)
                startActivity(this)
            }
        }
        if (item.itemId == MENU_ID_HAPUS) {
            AppExecutors.getInstance().diskIO().execute {
                AppDatabaseHelper.getInstance(this).folderDao().delete(
                    FolderEntity(folderId, "")
                )
                finish()
            }

        }
        if (item.itemId == MENU_ID_CREATE_NOTES) {
            Intent(this, NotesCreateActivity::class.java).run {
                putExtra("id", folderId)
                startActivity(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}