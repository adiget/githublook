package com.example.githublook.network.mappers

interface EntityMapper<M, E> {
    fun mapFromModel(model: M): E
}
