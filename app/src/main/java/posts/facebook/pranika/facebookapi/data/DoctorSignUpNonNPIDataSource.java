//package posts.facebook.pranika.facebookapi.data;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.inject.Inject;
//
//import io.reactivex.Flowable;
//import io.reactivex.Single;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import posts.facebook.pranika.facebookapi.Doctor;
//import retrofit2.Call;
//import retrofit2.Retrofit;
//import retrofit2.http.Multipart;
//import retrofit2.http.POST;
//import retrofit2.http.Part;
//import retrofit2.http.PartMap;
//
///**
// * Created by nikhiljain on 2/23/18.
// */
//
//public class DoctorSignUpNonNPIDataSource {
//
//    DoctorSignUpNonNPI doctorSignUpNonNPI;
//
//    @Inject
//    public DoctorSignUpNonNPIDataSource(Retrofit retrofit) {
//        this.doctorSignUpNonNPI = retrofit.create(DoctorSignUpNonNPI.class);
//    }
//
//
//
//    public interface DoctorSignUpNonNPI{
//
//        @Multipart
//
//        @POST("Doctors/signUpDoctors/")
//        Single<Doctor> uploadfile(
//                @PartMap() Map<String, RequestBody> partMap,
//                @Part MultipartBody.Part file
//                );
//    }
//
//   public Single<Doctor> networkUploadFile(HashMap map, MultipartBody.Part file){
//      return doctorSignUpNonNPI.uploadfile(map,file);
//  }
//}
