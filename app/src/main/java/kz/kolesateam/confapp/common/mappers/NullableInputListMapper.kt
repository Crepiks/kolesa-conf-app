package kz.kolesateam.confapp.common.mappers

interface NullableInputListMapper<I, O> : Mapper<List<I>?, List<O>>

class NullableInputListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : NullableInputListMapper<I, O> {

    override fun map(input: List<I>?): List<O> {
        return input?.map { mapper.map(it) }.orEmpty()
    }
}