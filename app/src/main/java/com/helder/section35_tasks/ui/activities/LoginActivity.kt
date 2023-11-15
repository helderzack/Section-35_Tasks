package com.helder.section35_tasks.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.helder.section35_tasks.R
import com.helder.section35_tasks.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.textCreateAccountAction.setOnClickListener(this)
        binding.buttonLogin.setOnClickListener(this)
    }

    override fun onClick(item: View) {
        when (item) {
            binding.textCreateAccountAction -> {
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }

            binding.buttonLogin -> {
                if (checkCredentials()) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
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