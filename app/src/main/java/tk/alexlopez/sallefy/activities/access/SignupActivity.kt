package tk.alexlopez.sallefy.activities.access

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.activities.MainActivity
import tk.alexlopez.sallefy.models.User
import tk.alexlopez.sallefy.models.UserRegister
import tk.alexlopez.sallefy.models.UserToken
import tk.alexlopez.sallefy.network.callback.UserCallback
import tk.alexlopez.sallefy.network.manager.UserManager
import tk.alexlopez.sallefy.utils.Session

class SignupActivity : AppCompatActivity(), UserCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initViews()
    }

    private fun initViews() {

        // back to login activity
        sign_up_login.setOnClickListener { finish() }
        sign_up.setOnClickListener {
            val username = sign_up_username.text.toString()
            val password = sign_up_password.text.toString()
            val email = recovery_email.text.toString()
            Session.getInstance(applicationContext).userRegister = UserRegister(email, username, password)
            UserManager.getInstance(applicationContext).registerAttempt(email, username, password, this@SignupActivity)
        }
    }

    private fun doLogin(username: String, userpassword: String) {
        UserManager.getInstance(applicationContext)
                .loginAttempt(username, userpassword)
                .subscribe ({
                    onLoginSuccess(it)
                }, {
                    Log.e("", "", it)
                })
    }

    override fun onLoginSuccess(userToken: UserToken) {
        Session.getInstance(applicationContext).userToken = userToken
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginFailure(throwable: Throwable) {
        Session.getInstance(applicationContext).userRegister = null
    }

    override fun onRegisterSuccess() {
        val userData = Session.getInstance(applicationContext).userRegister
        doLogin(userData.login, userData.password)
    }

    override fun onRegisterFailure(throwable: Throwable) {
        Session.getInstance(applicationContext).userRegister = null
        Toast.makeText(applicationContext, "Register failed", Toast.LENGTH_LONG).show()
    }

    override fun onUserInfoReceived(userData: User) {}
    override fun onFailure(throwable: Throwable) {}
}