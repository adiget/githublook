package com.example.githublook.presentation.mapper

import com.example.githublook.domain.model.GitSingleRepo
import com.example.githublook.presentation.views.views.SingleRepoView
import javax.inject.Inject

class SingleGitRepoViewMapper @Inject constructor() : Mapper<SingleRepoView, GitSingleRepo> {

    override fun mapToView(type: GitSingleRepo): SingleRepoView {
        return SingleRepoView(
            repoName = type.repoName,
            repoDescription = type.repoDescription,
            forksCount = type.forksCount
        )
    }
}