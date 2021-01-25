package skydu.android.instaclone.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PostDetailViewModelFactory(val postId: Int) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostDetailViewModel(postId) as T
    }
}