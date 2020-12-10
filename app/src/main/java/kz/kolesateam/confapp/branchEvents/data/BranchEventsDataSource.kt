package kz.kolesateam.confapp.branchEvents.data

import kz.kolesateam.confapp.branchEvents.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BranchEventsDataSource {

    @GET("/branch_events/{branchId}")
    fun getBranchEvents(@Path("branchId") branchId: Int): Call<List<EventApiData>>
}