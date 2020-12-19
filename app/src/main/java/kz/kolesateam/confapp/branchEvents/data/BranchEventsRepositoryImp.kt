package kz.kolesateam.confapp.branchEvents.data

import kz.kolesateam.confapp.branchEvents.data.mappers.EventApiDataMapper
import kz.kolesateam.confapp.branchEvents.data.models.EventApiData
import kz.kolesateam.confapp.branchEvents.domain.BranchEventsRepository
import kz.kolesateam.confapp.common.mappers.ListMapperImpl
import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.common.models.EventData
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository

class BranchEventsRepositoryImp(
    private val branchEventsDataSource: BranchEventsDataSource,
    private val favoritesRepository: FavoritesRepository
) : BranchEventsRepository {

    private val eventApiDataList: MutableList<EventApiData> = mutableListOf()

    override fun getBranchEvents(): List<EventData> =
        getMappedEventDataList(eventApiDataList)

    override fun loadBranchEvents(branchId: Int): ResponseData<List<EventData>, String> = try {
        val response = branchEventsDataSource.getBranchEvents(branchId = branchId).execute()
        if (response.isSuccessful) {
            val body = response.body()!!
            setEventApiDataList(body)
            val eventDataList = getMappedEventDataList(body)
            ResponseData.Success(eventDataList)
        } else {
            ResponseData.Error(response.errorBody().toString())
        }
    } catch (e: Exception) {
        ResponseData.Error(e.localizedMessage)
    }

    private fun getMappedEventDataList(eventApiDataList: List<EventApiData>): List<EventData> {
        return ListMapperImpl(
            EventApiDataMapper(
                favoritesRepository = favoritesRepository
            )
        ).map(eventApiDataList)
    }

    private fun setEventApiDataList(eventApiDataList: List<EventApiData>) {
        this.eventApiDataList.clear()
        this.eventApiDataList.addAll(eventApiDataList)
    }
}