package skydu.android.offlinenotes.pages.folder.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import skydu.android.offlinenotes.data.DataUtil
import skydu.android.offlinenotes.data.database.AppDatabaseHelper
import skydu.android.offlinenotes.databinding.ActivityFolderCreateBinding
import skydu.android.offlinenotes.databinding.ActivityFolderEditBinding
import skydu.android.offlinenotes.util.AppExecutors
import java.util.*

class FolderEditActivity : AppCompatActivity() {
    var folderId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFolderEditBinding.inflate(layoutInflater)

        folderId = intent.getIntExtra("id", -1)

        AppExecutors.getInstance().run {
            diskIO().execute {
                val database = AppDatabaseHelper.getInstance(this@FolderEditActivity)
                val folder = database.folderDao().getById(folderId)
                mainThread().execute {
                    binding.etJudul.setText(folder.judul)
                    binding.btnSave.setOnClickListener {
                        val judul = binding.etJudul.text.toString()
                        if (judul.isEmpty()) {
                            binding.etJudul.error = "Tidak boleh kosong"
                        } else {
                            diskIO().execute {
                                folder.judul = binding.etJudul.text.toString()
                                database.folderDao().update(folder)
                                finish()
                            }
                        }
                    }
                }
            }
        }
        setContentView(binding.root)
    }
}