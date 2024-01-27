package com.helder.section35_tasks.util

import android.content.Context
import androidx.biometric.BiometricManager

class BiometricHelper {

    companion object {

        fun isBiometricAuthenticationAvailable(context: Context): Boolean {
            val biometricManager = BiometricManager.from(context)

            val canAuthenticate = when(biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                BiometricManager.BIOMETRIC_SUCCESS -> true
                BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> false
                BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> false
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> false
                BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> false
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> false
                else -> false
            }

            return canAuthenticate
        }

    }

}