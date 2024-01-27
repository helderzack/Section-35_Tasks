package com.helder.section35_tasks.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.helder.section35_tasks.R
import com.helder.section35_tasks.databinding.ActivityLoginBinding
import com.helder.section35_tasks.ui.viewmodel.LoginViewModel
import com.helder.section35_tasks.util.BiometricHelper
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.textCreateAccountAction.setOnClickListener(this)
        binding.buttonLogin.setOnClickListener(this)

        viewModel.verifyAuthentication()

        observe()
    }

    private fun observe() {
        viewModel.loggedUser.observe(
            this
        ) {
            if (it) {
                promptBiometricLogin()
            } else {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        getAndSavePriorities()
                    }
                }
            }
        }

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
    }

    private suspend fun getAndSavePriorities() {
        viewModel.getPriorities()

        viewModel.receivedPriorities.collect {
            viewModel.savePriorities(it)
        }
    }

    override fun onClick(item: View) {
        when (item) {
            binding.textCreateAccountAction -> {
                startActivity(Intent(applicationContext, SignUpActivity::class.java))
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

    private fun promptBiometricLogin() {
        if (BiometricHelper.isBiometricAuthenticationAvailable(this)) {
            executor = ContextCompat.getMainExecutor(this)

            biometricPrompt =
                BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(
                            applicationContext,
                            "Authentication Error: $errString",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(
                            applicationContext,
                            "Authentication Failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(
                            applicationContext,
                            "User is authenticated",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }
                })

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login!")
                .setSubtitle("Please log in using your biometric credential")
                .setNegativeButtonText("Don't authenticate")
                .build()

            biometricPrompt.authenticate(promptInfo)
        }
    }

}