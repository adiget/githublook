package com.example.githublook.presentation.mapper

import com.example.githublook.domain.model.PullRequest
import com.example.githublook.presentation.views.views.PullRequestView
import com.example.githublook.presentation.views.views.utils.DateTimeUtils
import javax.inject.Inject

class PullRequestViewMapper @Inject constructor(private val userViewMapper: UserViewMapper) :
    Mapper<PullRequestView, PullRequest> {

    override fun mapToView(type: PullRequest): PullRequestView {
        return PullRequestView(
            id = type.id,
            prDesc = type.desc,
            closedAt = getValidDate(type.closedAt),
            createdAt = getValidDate(type.createdAt),
            user = userViewMapper.mapToView(type.user),
            prTitle = type.title
        )
    }

    private fun getValidDate(date : String) : String {
        return if(date.isValid()){
            DateTimeUtils.getDayWithMonthName(date)
        } else
            "No date available."
    }
}

fun String?.isValid(): Boolean {
    return !this.isNullOrEmpty() && this.isNotBlank()
}