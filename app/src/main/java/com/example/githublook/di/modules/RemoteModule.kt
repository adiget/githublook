package com.example.githublook.di.modules

import com.example.githublook.data.datasource.network.GitRemote
import com.example.githublook.data.datasource.network.GitRemoteRepositoryImp
import com.example.githublook.network.GithubService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {
    @Binds
    fun bindsGitRemote(
        gitRemoteImp: GitRemoteRepositoryImp
    ): GitRemote
}