package de.htwk.musicmanager.data.source.network

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

class UnwrapInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val response = chain.proceed(request)
        val body = response.body()
        val json = JSONObject(body?.string())

        val result = unwrap(json)

        return response.newBuilder().
            body(ResponseBody.create(body?.contentType(), result))
            .build()
    }

    private fun unwrap(json: JSONObject) : String{
        return when {
            json.has("results") -> {
                json.getJSONObject("results").getJSONObject("artistmatches").
                    getJSONArray("artist").toString()
            }
            json.has("topalbums") -> {
                json.getJSONObject("topalbums").getJSONArray("album").toString()
            }
            else -> {
                json.toString()
            }
        }
    }
}