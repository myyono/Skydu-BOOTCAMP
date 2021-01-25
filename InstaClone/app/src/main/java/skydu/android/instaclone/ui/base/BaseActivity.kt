package skydu.android.instaclone.ui.base

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import skydu.android.instaclone.R
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.ui.login.LoginActivity
import skydu.android.instaclone.utils.LoadingDialog

open class BaseActivity: AppCompatActivity() {


    protected fun setupBaseObservers(viewModel: BaseViewModel) {
        val logoutDialog = LoadingDialog.get(this, getString(R.string.logout))

        viewModel.loggedOutEvent.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    logoutDialog.dismiss()
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                }
                DataResult.State.LOADING -> {
                    logoutDialog.show()
                }
                else -> {
                    it.errorMessage?.run {
                        Toast.makeText(this@BaseActivity, this, Toast.LENGTH_SHORT).show()
                    }
                    logoutDialog.dismiss()
                }
            }
        }
    }

}