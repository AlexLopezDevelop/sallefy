package tk.alexlopez.sallefy.access

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
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
//        setContentView(R.layout.activity_login)
        // Binding is the reference to the R.layout.activity_login
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        // ViewModelFactory create a new instance when it create the activity.
        val model = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)
        binding.model = model
        binding.lifecycleOwner = this

        model.userManager = UserManager.getInstance(application)

        model.path.value = "android.resource://" + packageName + "/" + R.raw.login_video
        model.userToken.observe(this, Observer {
            onLoginSuccess(it)
        })
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

    }


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