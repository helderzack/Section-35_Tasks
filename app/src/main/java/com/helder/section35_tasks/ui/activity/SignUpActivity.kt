package com.helder.section35_tasks.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.helder.section35_tasks.databinding.ActivitySignUpBinding
import com.helder.section35_tasks.ui.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.buttonRegister.setOnClickListener {
            val name = binding.editName.text.toString()
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.create(name, email, password)
                }
            }
        }

        observe()
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        return super.onSupportNavigateUp()
    }

    private fun observe() {
        viewModel.signUp.observe(this) {
            if(it.status()) {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }
        }
    }
}