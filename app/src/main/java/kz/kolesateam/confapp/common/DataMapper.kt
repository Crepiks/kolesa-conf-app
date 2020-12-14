package kz.kolesateam.confapp.common

interface DataMapper<DTO, DomainModel> {

    fun map(data: DTO?): DomainModel
}