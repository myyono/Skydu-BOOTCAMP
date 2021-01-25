package skydu.android.offlinenotes.pages.folder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import skydu.android.offlinenotes.R
import skydu.android.offlinenotes.data.DataUtil
import skydu.android.offlinenotes.data.Folder
import skydu.android.offlinenotes.data.database.AppDatabaseHelper
import skydu.android.offlinenotes.databinding.ActivityFolderListBinding
import skydu.android.offlinenotes.pages.folder.create.FolderCreateActivity
import skydu.android.offlinenotes.pages.folder.detail.FolderDetailActivity
import skydu.android.offlinenotes.pages.folder.detail.FolderListAdapter
import skydu.android.offlinenotes.util.AppExecutors
import skydu.android.offlinenotes.util.ItemOffsetDecoration

class FolderListActivity : AppCompatActivity() {

    lateinit var folderListAdapter: FolderListAdapter

    private val MENU_ID_CREATE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFolderListBinding.inflate(layoutInflater)

        val onItemClick = { folder: Folder ->
            Intent(this, FolderDetailActivity::class.java).run {
                putExtra("id", folder.id)
                startActivity(this)
            }
            Unit
        }

        folderListAdapter = FolderListAdapter(onItemClick)

        val padding = resources.getDimensionPixelSize(R.dimen.padding) / 4
        binding.recyclerView.run {
            clipToPadding = false
            setPadding(padding, padding, padding, padding)
            addItemDecoration(ItemOffsetDecoration(padding))

            layoutManager = GridLayoutManager(this@FolderListActivity, 2)
            adapter = folderListAdapter
        }
        setContentView(binding.recyclerView)
    }


    override fun onResume() {
        super.onResume()
        AppExecutors.getInstance().run {
            diskIO().execute {
                val folders = AppDatabaseHelper.getInstance(this@FolderListActivity).folderDao().getAll()
                mainThread().execute {
                    folderListAdapter.submitData(DataUtil.convertFromFolderEntityList(folders))
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.run {
            menu.add(0, MENU_ID_CREATE, 0, "Buat Folder Baru")
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == MENU_ID_CREATE) {
            Intent(this, FolderCreateActivity::class.java).run {
                startActivity(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}