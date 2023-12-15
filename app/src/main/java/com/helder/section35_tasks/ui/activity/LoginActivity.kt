package com.helder.section35_tasks.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.helder.section35_tasks.R
import com.helder.section35_tasks.databinding.ActivityLoginBinding
import com.helder.section35_tasks.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.textCreateAccountAction.setOnClickListener(this)
        binding.buttonLogin.setOnClickListener(this)

        viewModel.verifyLoggedUser()

        observe()
    }

    private fun observe() {
        viewModel.login.observe(
            this
        ) {
            if (it.status()) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    it.message(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.loggedUser.observe(
            this
        ) {
            if (it) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }

    }

    override fun onClick(item: View) {
        when (item) {
            binding.textCreateAccountAction -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }

            binding.buttonLogin -> {
                handleLogin()
            }
        }
    }

    private fun handleLogin() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (checkCredentials()) {
                    viewModel.doLogin(
                        binding.editEmail.text.toString(),
                        binding.editPassword.text.toString()
                    )
                }
            }
        }
    }

    private fun checkCredentials(): Boolean {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        if (email.isBlank() || email.isEmpty()
            || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        ) {
            Toast.makeText(
                this,
                getString(R.string.invalid_email_address_alert),
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        if (password.isBlank() || password.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.invalid_password_alert),
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        return true
    }
}