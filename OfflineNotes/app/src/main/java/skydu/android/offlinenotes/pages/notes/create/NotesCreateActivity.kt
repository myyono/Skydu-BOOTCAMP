package skydu.android.offlinenotes.pages.notes.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import skydu.android.offlinenotes.data.DataUtil
import skydu.android.offlinenotes.data.database.AppDatabaseHelper
import skydu.android.offlinenotes.data.database.notes.NotesEntity
import skydu.android.offlinenotes.databinding.ActivityNotesCreateBinding
import skydu.android.offlinenotes.util.AppExecutors

class NotesCreateActivity : AppCompatActivity() {

    var folderId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNotesCreateBinding.inflate(layoutInflater)

        folderId = intent.getIntExtra("id", -1)

        binding.btnSave.setOnClickListener {
            val judul = binding.etJudul.text.toString()

            val isi = binding.etIsi.text.toString()

            var success = true
            if(judul.isEmpty()) {
                binding.etJudul.error = "Tidak boleh kosong"
                success = false
            }

            if(isi.isEmpty()) {
                binding.etIsi.error = "Tidak boleh kosong"
                success = false
            }

            if(success) {
                AppExecutors.getInstance().diskIO().execute {
                    AppDatabaseHelper.getInstance(this).notesDao().insert(
                        NotesEntity(
                            0,
                            judul,
                            isi,
                            folderId
                        )
                    )
                }
                finish()
            }
        }
        setContentView(binding.root)
    }
}