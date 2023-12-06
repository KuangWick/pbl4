package com.ssn.studentapp

import android.content.Context
import java.util.Locale

class LocaleHelper {
    companion object {
        fun setLocale(context: Context, locale: Locale): Context {
            val newConfig = context.resources.configuration
            newConfig.setLocale(locale)
            val ctx = context.createConfigurationContext(newConfig)
            return ctx.apply {
                resources.updateConfiguration(newConfig, resources.displayMetrics)
            }
        }
    }
}