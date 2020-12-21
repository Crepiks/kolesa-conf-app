package kz.kolesateam.confapp.eventDetails.data

import kz.kolesateam.confapp.eventDetails.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventDetailsDataSource {

    @GET("/events/{eventId}")
    fun getEvent(@Path("eventId") eventId: Int): Call<EventApiData>
}