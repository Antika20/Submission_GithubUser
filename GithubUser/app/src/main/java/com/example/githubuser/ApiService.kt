package com.example.githubuser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("search/users")
    suspend fun getUser(
        @Query("q") query: String ,
    ): SearchResponse

    @GET("users/{username}")
    suspend fun  getDetailUser(
        @Path("username") username : String
    ): UserResponse

    @GET("users/{username}/followers")
    suspend fun  getFollowers(
        @Path("username")username: String
    ): List<UserResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username")username: String
    ):List<UserResponse>
}