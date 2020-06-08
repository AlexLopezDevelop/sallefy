package tk.alexlopez.sallefy.activities.access

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.activities.MainActivity
import tk.alexlopez.sallefy.databinding.ActivityLoginBinding
import tk.alexlopez.sallefy.models.User
import tk.alexlopez.sallefy.models.UserToken
import tk.alexlopez.sallefy.network.callback.UserCallback
import tk.alexlopez.sallefy.network.manager.UserManager
import tk.alexlopez.sallefy.utils.Session

class LoginActivity : AppCompatActivity(), UserCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        // Binding is the reference to the R.layout.activity_login
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        // ViewModelFactory create a new instance when it create the activity.
        val model = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)
        binding.model = model
        binding.lifecycleOwner = this

        model.userManager = UserManager.getInstance(application)

        // random video in login screen
        model.path.value = getRandomVideoPath()

        model.userToken.observe(this, Observer {
            onLoginSuccess(it)
        })

        model.doSignUp.observe(this, Observer {
            val intent = Intent(applicationContext, SignActivity::class.java)
            startActivity(intent)
        })

        model.doRecoverPass.observe(this, Observer {
            val intent = Intent(applicationContext, RecoveryPass::class.java)
            startActivity(intent)
        })
    }

    private fun getRandomVideoPath(): String {
        val videoNum = (1..2).random()
        val id = resources.getIdentifier("raw/login_video$videoNum", null, this.packageName)
        return "android.resource://$packageName/$id"
    }

    override fun onRestart() {
        super.onRestart()
        login_background.visibility = View.VISIBLE
    }

    override fun onLoginSuccess(userToken: UserToken) {
        if (userToken.idToken.isNotEmpty()) {
            Session.getInstance(applicationContext).userToken = userToken
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onLoginFailure(throwable: Throwable) {
        Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_LONG).show()
    }

    override fun onRegisterSuccess() {}
    override fun onRegisterFailure(throwable: Throwable) {}
    override fun onUserInfoReceived(userData: User) {}
    override fun onFailure(throwable: Throwable) {}
}