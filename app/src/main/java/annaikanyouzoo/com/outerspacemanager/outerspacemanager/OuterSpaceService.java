package annaikanyouzoo.com.outerspacemanager.outerspacemanager;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Amount;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.AttackUserResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetBuildingsResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetReportsResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetShipsResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetUserFleetResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetUserSearchesResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetUsersResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.NewBuildingResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.NewShipResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.SearchResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by annaikanyouzoo on 06/03/2017.
 */

public interface OuterSpaceService {

    // POST REQUESTS

    @POST("/api/v1/auth/create")
    Call<User> newUser(@Body User user);

    @POST("/api/v1/auth/login")
    Call<User> login(@Body User user);

    @POST("/api/v1/buildings/create/{buildingId}")
    Call<NewBuildingResponse> newBuilding(@Header("x-access-token") String token, @Path("buildingId") int buildingId);

    @POST("/api/v1/ships/create/{shipId}")
    Call<NewShipResponse> newShip(@Header("x-access-token") String token, @Path("shipId") int shipId, @Body Amount amount);

    @POST("/api/v1/fleet/attack/{userName}")
    Call<AttackUserResponse> attackUser(@Header("x-access-token") String token, @Path("userName") String userName);

    @POST("/api/v1/searches/create/{searchId}")
    Call<SearchResponse> search(@Header("x-access-token") String token, @Path("searchId") int searchId);

    // GET REQUESTS

    @GET("/api/v1/users/get")
    Call<User> getCurrentUser(@Header("x-access-token") String token);

    @GET("/api/v1/users/{from}/{limit}")
    Call<GetUsersResponse> getUsers(@Header("x-access-token") String token, @Path("from") int from, @Path("limit") int limit);

    @GET("/api/v1/buildings/list")
    Call<GetBuildingsResponse> getBuildings(@Header("x-access-token") String token);

    @GET("/api/v1/ships")
    Call<GetShipsResponse> getShips(@Header("x-access-token") String token);

    @GET("/api/v1/fleet/list")
    Call<GetUserFleetResponse> getUserFleet(@Header("x-access-token") String token);

    @GET("/api/v1/searches/list")
    Call<GetUserSearchesResponse> getUserSearches(@Header("x-access-token") String token);

    @GET("/api/v1/reports/{from}/{limit}")
    Call<GetReportsResponse> getReports(@Header("x-access-token") String token, @Path("from") int from, @Path("limit") int limit);

}
