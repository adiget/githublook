package com.example.githublook.data.datasource

import javax.inject.Inject

class GitDataSourceFactory @Inject constructor(
    private val gitRemoteDataSource: GitRemoteDataSource
) {

    fun getRemoteDataSource(): GitDataSource {
        return gitRemoteDataSource
    }
}