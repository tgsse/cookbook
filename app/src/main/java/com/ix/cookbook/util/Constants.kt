package com.ix.cookbook.util

import com.ix.cookbook.BuildConfig

sealed class Constants {
    companion object {
        const val apiKey = BuildConfig.apiKey
        const val baseUrl = BuildConfig.baseUrl

        const val maxSearchLength = 25
    }
}
