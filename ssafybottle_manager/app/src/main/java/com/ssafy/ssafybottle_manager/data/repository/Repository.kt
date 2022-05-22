package com.ssafy.ssafybottle_manager.data.repository

import android.content.Context

class Repository private constructor(context: Context) {


    companion object {
        private var instance: Repository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = Repository(context)
            }
        }

        fun get(): Repository {
            return instance ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}