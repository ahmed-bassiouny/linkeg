package net.link.linkapp;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {


    @POST("registration")
    @Multipart
    Call<BaseResponse> signup(@Part MultipartBody.Part file , @Part("name") RequestBody name
            , @Part("email") RequestBody email, @Part("password") RequestBody password
            , @Part("phone") RequestBody phone
            , @Part("type") RequestBody type, @Part("category") RequestBody category);


    @POST("login")
    @FormUrlEncoded()
    Call<BaseResponse> login(@Field("email") String email, @Field("password") String password);
}
