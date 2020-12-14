package kz.kolesateam.confapp.upcomingEvents.data

import kz.kolesateam.confapp.upcomingEvents.data.models.BranchApiData
import retrofit2.Call
import retrofit2.http.GET

interface UpcomingEventsApiClient {

    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>

}