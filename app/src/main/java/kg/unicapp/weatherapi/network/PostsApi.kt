package kg.unicapp.weatherapi.network

import kg.unicapp.weatherapi.models.Post
import retrofit2.Call
import retrofit2.http.*

interface PostsApi {

    @GET("posts/(id)")
    fun fetchPostById(
        @Path("id") id: Int =1
    ): Call<Post>

    @POST("posts")
    fun createPost(
        @Body post: Post
    ): Call<Post>

    @POST("posts")
    @FormUrlEncoded
    fun createPostUsingFields(
       @Field("userId") userId: Int,
       @Field("title") title: String,
       @Field("body") body: String
    ): Call<Post>

    @POST("posts")
    @FormUrlEncoded
    fun createPostUsingFieldsMap(

        @FieldMap map: Map<String, String>
    ): Call<Post>
}