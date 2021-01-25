package skydu.android.offlinenotes.pages.folder.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import skydu.android.offlinenotes.data.DataUtil
import skydu.android.offlinenotes.data.database.AppDatabase
import skydu.android.offlinenotes.data.database.AppDatabaseHelper
import skydu.android.offlinenotes.data.database.folder.FolderEntity
import skydu.android.offlinenotes.databinding.ActivityFolderCreateBinding
import skydu.android.offlinenotes.databinding.ActivityFolderListBinding
import skydu.android.offlinenotes.util.AppExecutors

class FolderCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFolderCreateBinding.inflate(layoutInflater)

        binding.btnSave.setOnClickListener {
            binding.etJudul.text.toString().run {
                if(this.isEmpty()) {
                    binding.etJudul.error = "Tidak boleh kosong"
                } else {
                    AppExecutors.getInstance().diskIO().execute {
                        AppDatabaseHelper.getInstance(this@FolderCreateActivity).folderDao().insert(
                            FolderEntity(
                                0,
                                this
                            )
                        )
                        finish()
                    }
                }
            }
        }
        setContentView(binding.root)
    }
}