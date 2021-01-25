package skydu.android.instaclone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.Dispatchers
import skydu.android.instaclone.data.local.prefs.UserPreferences
import skydu.android.instaclone.data.remote.NetworkService
import skydu.android.instaclone.data.remote.Networking
import skydu.android.instaclone.data.remote.login.LoginRequest
import skydu.android.instaclone.data.remote.login.LoginResponse
import skydu.android.instaclone.data.repository.model.DataResult

class UserRepository {
    private val networkService: NetworkService = Networking.service
    private val userPreferences = UserPreferences()

    fun doUserLogin(username: String, password: String): LiveData<DataResult<Unit>> =
        liveData(Dispatchers.IO) {
            emit(DataResult<Unit>(DataResult.State.LOADING, null, null))

            val result =
                try {
                    networkService.doLoginCall(LoginRequest(username, password))
                        .convertToDataResult()
                } catch (e: Exception) {
                    e.convertExceptionToError()
                }

            if (result.state == DataResult.State.SUCCESS) {
                result.data?.run {
                    saveCurrentUser(this)
                    emit(
                        DataResult(
                            DataResult.State.SUCCESS, Unit, null
                        )
                    )
                }
            } else {
                emit(DataResult<Unit>(result.state, null, result.errorMessage))
            }
        }

    fun doLogout(): LiveData<DataResult<Unit>> = isUserLoggedIn().switchMap {
        liveData(Dispatchers.IO) {
            if (!it) {
                emit(
                    DataResult(
                        DataResult.State.SUCCESS, Unit, null
                    )
                )
                return@liveData
            }
            emit(DataResult<Unit>(DataResult.State.LOADING, null, null))

            val result =
                try {
                    networkService.doLogout().convertToDataResult()
                } catch (e: Exception) {
                    e.convertExceptionToError()
                }

            if (result.state == DataResult.State.SUCCESS || result.state == DataResult.State.UNAUTHORIZED) {
                removeCurrentUser()
                emit(
                    DataResult(
                        DataResult.State.SUCCESS, Unit, null
                    )
                )
            } else {
                emit(DataResult<Unit>(result.state, null, result.errorMessage))
            }
        }
    }

    fun isUserLoggedIn(): LiveData<Boolean> = liveData(Dispatchers.IO) {
        emit(!userPreferences.getUserName().isNullOrEmpty())
    }

    private fun removeCurrentUser() {
        userPreferences.removeAccessToken()
        userPreferences.removeUserName()
    }

    private fun saveCurrentUser(loginReponse: LoginResponse) {
        userPreferences.setUserName(loginReponse.profile.username)
        userPreferences.setAccessToken(loginReponse.token)
    }
}