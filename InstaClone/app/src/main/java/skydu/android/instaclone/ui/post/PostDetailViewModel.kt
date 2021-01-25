package skydu.android.instaclone.ui.post

import androidx.lifecycle.*
import skydu.android.instaclone.data.repository.PostRepository
import skydu.android.instaclone.data.repository.model.CommentViewData
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.ui.base.BaseViewModel

class PostDetailViewModel(postId: Int) : BaseViewModel() {

    private val postRepository = PostRepository()
    private val _comment = MutableLiveData<String>()

    private val _triggerComment = MutableLiveData<Unit>()
    private val _triggerReload = MutableLiveData<Unit>()

    init {
        _triggerReload.value = Unit
    }


    val submitComment: LiveData<DataResult<Unit>> = _triggerComment
        .switchMap {
            postRepository.doComment(
                postId,
                _comment.value ?: ""
            )
        }.switchMap {
            liveData {
                if (it.state == DataResult.State.UNAUTHORIZED) {
                    triggerLogout()
                } else {
                    emit(it)
                    if(it.state == DataResult.State.SUCCESS) {
                        _triggerReload.value = Unit
                    }
                }
            }
        }


    val postDetails: LiveData<DataResult<List<CommentViewData>>> = _triggerReload.switchMap {
        postRepository.fetchPostDetail(postId).switchMap {
            liveData {
                if (it.state == DataResult.State.UNAUTHORIZED) {
                    triggerLogout()
                } else {
                    emit(it)
                }
            }
        }
    }

    val comment: LiveData<DataResult<String>> = _comment.map {
        if (it.isNullOrEmpty()) {
            return@map DataResult(DataResult.State.ERROR, null, null)
        } else {
            return@map DataResult(DataResult.State.SUCCESS, it, null)
        }
    }

    fun onEditTextChanged(text: String) {
        _comment.postValue(text)
    }

    fun doComment() {
        _triggerComment.value = Unit
    }
}