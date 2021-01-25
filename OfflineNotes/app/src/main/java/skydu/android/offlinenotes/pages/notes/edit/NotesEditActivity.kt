package skydu.android.offlinenotes.pages.notes.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import skydu.android.offlinenotes.data.DataUtil
import skydu.android.offlinenotes.data.database.AppDatabaseHelper
import skydu.android.offlinenotes.databinding.ActivityNotesCreateBinding
import skydu.android.offlinenotes.util.AppExecutors

class NotesEditActivity : AppCompatActivity() {
    var notesId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNotesCreateBinding.inflate(layoutInflater)

        notesId = intent.getIntExtra("id", -1)

        val database = AppDatabaseHelper.getInstance(this)

        AppExecutors.getInstance().run {
            diskIO().execute {
                val notes = database.notesDao().getById(notesId)
                mainThread().execute {
                    binding.etJudul.setText(notes.judul)
                    binding.etIsi.setText(notes.isi)
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
                            notes.judul = judul
                            notes.isi = isi
                            diskIO().execute {
                                database.notesDao().update(notes)
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