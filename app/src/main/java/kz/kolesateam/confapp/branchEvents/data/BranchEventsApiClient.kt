package kz.kolesateam.confapp.branchEvents.data

import kz.kolesateam.confapp.branchEvents.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BranchEventsApiClient {

    @GET("/branch_events/{eventId}")
    fun getBranchEvents(@Path("eventId") eventId: Int): Call<List<EventApiData>>
}