//package posts.facebook.pranika.facebookapi.data;
//
//import javax.inject.Inject;
//
//import io.reactivex.Flowable;
//import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;
//import retrofit2.Call;
//import retrofit2.Retrofit;
//import retrofit2.http.GET;
//import posts.facebook.pranika.facebookapi.Model.*;
//import retrofit2.http.Query;
//
//
///**
// * Created by nikhiljain on 1/28/18.
// */
//
//public class DoctorDataSource {
//
//    NPIDataService npiDataService;
//
//
//    @Inject
//    public DoctorDataSource(Retrofit retrofit) {
//
//
//        npiDataService=retrofit.create(NPIDataService.class);
//
//    }
//
//    public interface NPIDataService{
//
//        @GET("api")
//        Call<Result> getDoctorRepositories(@Query("number") String number);
// }
//
//    public Call<Result>  getDoctorRepositoriesImpl(String number){
//        return npiDataService.getDoctorRepositories(number);
//    }
//}
