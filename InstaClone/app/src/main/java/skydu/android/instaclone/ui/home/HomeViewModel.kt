package skydu.android.instaclone.ui.home

import androidx.lifecycle.*
import skydu.android.instaclone.data.repository.PostRepository
import skydu.android.instaclone.data.repository.UserRepository
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.data.repository.model.PostViewData
import skydu.android.instaclone.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    private val toggleLike: MutableLiveData<LikeData> = MutableLiveData()

    private val postRepository: PostRepository = PostRepository()

    private val page: MutableLiveData<Int> = MutableLiveData()

    private var isLoading = false

    init {
        page.postValue(1)
    }

    val posts: LiveData<DataResult<PostResult>> =
        page.switchMap { page ->
            postRepository.fetchHomePostList(page).switchMap {
                liveData {
                    isLoading = it.state == DataResult.State.LOADING
                    if (it.state == DataResult.State.UNAUTHORIZED) {
                        triggerLogout()
                    } else {
                        emit(
                            DataResult(
                                it.state,
                                PostResult(page == 1, it.data ?: emptyList()),
                                it.errorMessage,
                            )
                        )
                    }
                }
            }
        }

    val like: LiveData<DataResult<LikeData>> = toggleLike.switchMap { likeData ->
        postRepository.doToggleLike(likeData.postId).switchMap {
            liveData {
                if (it.state == DataResult.State.UNAUTHORIZED) {
                    triggerLogout()
                } else {
                    emit(
                        DataResult(
                            it.state,
                            likeData,
                            it.errorMessage
                        )
                    )
                }
            }
        }
    }

    private val _shareUrl = MutableLiveData<String>()

    val shareUrl: LiveData<String> = _shareUrl


    fun loadNextPage() {
        if (!isLoading) {
            page.postValue((page.value ?: 1) + 1)
        }
    }

    fun onLikeClicked(it: PostViewData) {
        toggleLike.postValue(LikeData(it.id, it.is_liked))
    }

    fun onShareClicked(it: PostViewData) {
        _shareUrl.postValue("https://www.instaclone.com/post/detail/" + it.id)
    }


    class LikeData(val postId: Int, val isCurrentlyLiked: Boolean)
    class PostResult(val firstPage: Boolean, val list: List<PostViewData>)
}