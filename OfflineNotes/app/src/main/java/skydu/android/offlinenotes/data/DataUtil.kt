package skydu.android.offlinenotes.data

import skydu.android.offlinenotes.data.database.folder.FolderEntity
import skydu.android.offlinenotes.data.database.notes.NotesEntity

object DataUtil {
    fun convertFromFolderEntityList(folders: List<FolderEntity>) : List<Folder> {
        val list = mutableListOf<Folder>()
        for(folder in folders) {
            list.add(
                convertFromFolderEntity(folder)
            )
        }
        return list
    }

    fun convertFromFolderEntity(folder: FolderEntity) : Folder {
        return Folder(
            folder.id,
            folder.judul
        )
    }

    fun convertFromNotesEntityList(notes: List<NotesEntity>) : List<Notes> {
        val list = mutableListOf<Notes>()
        for(note in notes) {
            list.add(
                convertFromNoteEntity(note)
            )
        }
        return list
    }

    fun convertFromNoteEntity(note: NotesEntity) : Notes {
        return Notes(
            note.id,
            note.judul,
            note.isi
        )
    }
}