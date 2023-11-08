package com.example.githublook.di.modules

import com.example.githublook.data.repository.DataRepository
import com.example.githublook.domain.GitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsGitRepository(
        gitRepository: DataRepository,
    ): GitRepository
}
