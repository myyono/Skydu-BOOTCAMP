package skydu.android.instaclone.ui.login

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.utils.ResourceUtil
import skydu.android.instaclone.R
import skydu.android.instaclone.data.repository.UserRepository

class LoginViewModel : ViewModel() {
    private val _emailValidation: MutableLiveData<DataResult<Unit>> = MutableLiveData()
    private val _passwordValidation: MutableLiveData<DataResult<Unit>> = MutableLiveData()
    private val loginAttempt: MutableLiveData<Pair<String, String>> = MutableLiveData()
    private val userRepository = UserRepository()


    val emailValidation: LiveData<DataResult<Unit>> = _emailValidation
    val passwordValidation: LiveData<DataResult<Unit>> = _passwordValidation

    val loginState: LiveData<DataResult<Unit>> = loginAttempt.switchMap {
        userRepository.doUserLogin(it.first, it.second)
    }.switchMap {
        liveData(Dispatchers.IO) {
            emit(it)
        }
    }

    fun onLogin(username: String, password: String) {
        var validated = true
        if (username.isBlank()) {
            validated = false
            _emailValidation.postValue(
                DataResult(
                    DataResult.State.ERROR,
                    Unit,
                    ResourceUtil.getString(R.string.username_field_empty)
                )
            )
        } else {
            _emailValidation.postValue(
                DataResult(DataResult.State.SUCCESS, Unit, null)
            )
        }

        if (password.isBlank()) {
            validated = false
            _passwordValidation.postValue(
                DataResult(
                    DataResult.State.ERROR,
                    Unit,
                    ResourceUtil.getString(R.string.password_field_empty)
                )
            )
        } else {
            _passwordValidation.postValue(
                DataResult(DataResult.State.SUCCESS, Unit, null)
            )
        }
        if (validated) {
            loginAttempt.postValue(Pair(username, password))
        }
    }
}