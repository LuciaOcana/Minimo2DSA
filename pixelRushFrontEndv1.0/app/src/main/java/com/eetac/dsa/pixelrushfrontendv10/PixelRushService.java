package com.eetac.dsa.pixelrushfrontendv10;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.LoginCredentials;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.RegisterCredentials;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.User;
import com.google.gson.JsonObject;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.PUT;

public interface PixelRushService {
    String URL = "http://10.0.2.2:8080/dsaApp/pixelRush/";
    @POST("registerNewUser")
    Call<Void> register(@Body RegisterCredentials credentials);
    @POST("login")
    Call<Void> login(@Body LoginCredentials credentials);
    @GET("getObjectListFromStore")
    Call<List<StoreObject>> getAllObjectsFromStore();
    @GET("{username}")
    Call<User> getUser(@Path("username") String username);
    @PUT("addItemToUser/{username}/{objectID}")
    Call<Void> addItemToUser(@Path("username") String username, @Path("objectID") String objectID);
    @GET("getMatchPointsFromActiveMatch/{username}")
    Call<JsonObject> getMatchPointsFromActiveMatch(@Path("username")String username);
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
