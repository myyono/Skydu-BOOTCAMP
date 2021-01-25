package skydu.android.instaclone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import skydu.android.instaclone.data.remote.NetworkService
import skydu.android.instaclone.data.remote.Networking
import skydu.android.instaclone.data.remote.comment.CommentRequest
import skydu.android.instaclone.data.repository.model.CommentViewData
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.data.repository.model.PostViewData
import skydu.android.instaclone.utils.TimeUtils

class PostRepository {
    private val networkService: NetworkService = Networking.service

    fun fetchHomePostList(page: Int): LiveData<DataResult<List<PostViewData>>> =
        liveData {
            emit(DataResult<List<PostViewData>>(DataResult.State.LOADING, null, null))

            val result =
                try {
                    networkService.getHomeFeed(page).convertToDataResult()
                } catch (e: Exception) {
                    e.convertExceptionToError()
                }

            if (result.state == DataResult.State.SUCCESS) {
                result.data?.map { item ->
                    PostViewData(
                        item.id,
                        item.username,
                        item.user_image_url,
                        item.image_url,
                        item.caption.let {
                            if (it.length > 100) {
                                it.substring(0, 100) + "..."
                            } else {
                                it
                            }
                        },
                        item.likes_info.count,
                        item.likes_info.is_liked,
                        TimeUtils.convertTimeFormat(item.created_at)
                    )
                }.run {
                    emit(
                        DataResult(
                            DataResult.State.SUCCESS,
                            this,
                            null
                        )
                    )
                }
            } else {
                emit(DataResult<List<PostViewData>>(result.state, null, result.errorMessage))
            }
        }

    fun doToggleLike(postId: Int): LiveData<DataResult<Unit>> =
        liveData {
            emit(DataResult<Unit>(DataResult.State.LOADING, null, null))

            val result =
                try {
                    networkService.doToggleLike(postId).convertToDataResult()
                } catch (e: Exception) {
                    e.convertExceptionToError()
                }

            emit(DataResult<Unit>(result.state, null, result.errorMessage))

        }

    fun fetchPostDetail(postId: Int): LiveData<DataResult<List<CommentViewData>>> =
        liveData {
            emit(DataResult<List<CommentViewData>>(DataResult.State.LOADING, null, null))

            val result =
                try {
                    networkService.getPostDetail(postId).convertToDataResult()
                } catch (e: Exception) {
                    e.convertExceptionToError()
                }

            if (result.state == DataResult.State.SUCCESS) {
                result.data?.let { item ->
                    arrayListOf<CommentViewData>().apply {
                        add(
                            CommentViewData(
                                item.id,
                                item.username,
                                item.user_image_url,
                                item.caption,
                                TimeUtils.convertTimeFormat(item.created_at)
                            )
                        )

                        item.comments.forEach {
                            add(
                                CommentViewData(
                                    it.id,
                                    "" + it.user_id,
                                    "",//it.user_image_url,
                                    it.text,
                                    TimeUtils.convertTimeFormat(it.created_at)
                                )
                            )
                        }
                    }
                }?.run {
                    emit(
                        DataResult(
                            DataResult.State.SUCCESS,
                            (this as List<CommentViewData>),
                            null
                        )
                    )
                }
            } else {
                emit(DataResult<List<CommentViewData>>(result.state, null, result.errorMessage))
            }
        }

    fun doComment(postId: Int, comment: String): LiveData<DataResult<Unit>> = liveData {
        emit(DataResult<Unit>(DataResult.State.LOADING, null, null))

        val result =
            try {
                networkService.doComment(CommentRequest(postId, comment)).convertToDataResult()
            } catch (e: Exception) {
                e.convertExceptionToError()
            }

        emit(result)
    }
}


