package kz.kolesateam.confapp.upcomingEvents.data

import kz.kolesateam.confapp.common.mappers.ListMapperImpl
import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.events.domain.models.BranchData
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository
import kz.kolesateam.confapp.upcomingEvents.data.mappers.BranchApiDataMapper
import kz.kolesateam.confapp.upcomingEvents.data.models.BranchApiData
import kz.kolesateam.confapp.upcomingEvents.domain.UpcomingEventsRepository

class UpcomingEventsRepositoryImp(
    private val upcomingEventsDataSource: UpcomingEventsDataSource,
    private val favoritesRepository: FavoritesRepository
) : UpcomingEventsRepository {

    private val branchApiDataList: MutableList<BranchApiData> = mutableListOf()

    override fun getUpcomingEvents(): List<BranchData> =
        getMappedUpcomingEvents(branchApiDataList)

    override fun loadUpcomingEvents(): ResponseData<List<BranchData>, String> = try {
        val response = upcomingEventsDataSource.getUpcomingEvents().execute()
        if (response.isSuccessful) {
            val body = response.body()!!
            setUpcomingEventApiDataList(body)
            val branchDataList = getMappedUpcomingEvents(body)
            ResponseData.Success(branchDataList)
        } else {
            ResponseData.Error(response.errorBody().toString())
        }
    } catch (e: Exception) {
        ResponseData.Error(e.localizedMessage)
    }

    private fun getMappedUpcomingEvents(branchApiDataList: List<BranchApiData>): List<BranchData> =
        ListMapperImpl(
            BranchApiDataMapper(
                favoritesRepository = favoritesRepository
            )
        ).map(branchApiDataList)

    private fun setUpcomingEventApiDataList(branchApiDataList: List<BranchApiData>) {
        this.branchApiDataList.clear()
        this.branchApiDataList.addAll(branchApiDataList)
    }
}