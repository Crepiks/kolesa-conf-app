package kz.kolesateam.confapp.common.mappers

interface Mapper<I, O> {

    fun map(data: I): O
}