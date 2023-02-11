package com.example.taskapp.utils

import android.content.Context

class Preferences(private val context: Context) {

    companion object {
        const val ON_BOARDING_STATE = "have seen on boarding"
        const val PROFILE_TITLE_STATE_KEY = "saved text"
        const val PROFILE_PICTURE_KEY = "picture state"
    }

    private val prefs = context.getSharedPreferences(
        "utils",
        Context.MODE_PRIVATE
    )

    fun setHaveSeenOnBoarding() {
        prefs.edit().putBoolean(ON_BOARDING_STATE, true).apply()
    }

    fun isHaveSeenOnBoarding() = prefs.getBoolean(ON_BOARDING_STATE, false)

    fun setPrefTitle(str: String) {
        prefs.edit().putString(PROFILE_TITLE_STATE_KEY, str).apply()
    }

    fun getPrefTitle() = prefs.getString(PROFILE_TITLE_STATE_KEY, "")

    fun setPrefImage(str: String) {
        prefs.edit().putString(PROFILE_PICTURE_KEY, str).apply()
    }

    fun getPrefImage() = prefs.getString(PROFILE_PICTURE_KEY, "")



}