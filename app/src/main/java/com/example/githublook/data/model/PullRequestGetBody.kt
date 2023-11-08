package com.example.githublook.data.model

data class PullRequestGetBody(
    var userName : String,
    var repositoryName : String,
    var state : String
)