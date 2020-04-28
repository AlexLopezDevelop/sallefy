package tk.alexlopez.sallefy.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.models.User
import tk.alexlopez.sallefy.models.UserToken
import tk.alexlopez.sallefy.network.callback.UserCallback
import tk.alexlopez.sallefy.network.manager.UserManager
import tk.alexlopez.sallefy.utils.Session

//Prueba Alfredo
class LoginActivity : AppCompatActivity(), UserCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onResume() {
        super.onResume()
        initViews()
    }

    override fun onRestart() {
        super.onRestart()
        login_background.visibility = View.VISIBLE
    }

    private fun initViews() {

        // Go to registration page
        login_sign_up.setOnClickListener {
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivity(intent)
        }

        // Start login view
        login_button.setOnClickListener {
            doLogin(login_username.text.toString(), login_password.text.toString())
        }

        // Video background configuration
        val videoLoginPath = "android.resource://" + packageName + "/" + R.raw.login_video
        videoLogin.setVideoPath(videoLoginPath)
        videoLogin.start()
    }

    private fun doLogin(username: String, password: String) =
        UserManager.getInstance(applicationContext)
                .loginAttempt(username, password, this@LoginActivity)

    override fun onLoginSuccess(userToken: UserToken) {
        Session.getInstance(applicationContext).userToken = userToken
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginFailure(throwable: Throwable) {
        Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_LONG).show()
    }

    override fun onRegisterSuccess() {}
    override fun onRegisterFailure(throwable: Throwable) {}
    override fun onUserInfoReceived(userData: User) {}
    override fun onFailure(throwable: Throwable) {}
}