package com.example.githublook.data.datasource

import javax.inject.Inject

class GitDataSourceFactory @Inject constructor(
    private val githubLookRemoteDataSource: GithubLookNetworkDataSource
) {

    fun getRemoteDataSource(): GithubLookDataSource {
        return githubLookRemoteDataSource
    }
}