package posts.facebook.pranika.facebookapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

public class NonNPISignUp extends BaseActivity {


    private static final String USER_CREATION_SUCCESS = "Successfully created user";
    private static final String USER_CREATION_ERROR = "User creation error";

    @BindView(R.id.camera)
    TextView camera;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.phoneno)
    EditText phone;
    @BindView(R.id.specialization)
    EditText specialization;
    @BindView(R.id.signdoctor)
    Button signup;
    @BindView(R.id.image)
    ImageView imageView;

    FirebaseAuth mAuth;



    @BindView(R.id.gallery)

    TextView gallery;
    String imagePath="";
    Uri file=null;
    boolean cameraclick=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_npisign_up);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance(myApp);



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(NonNPISignUp.this)
                    .withPermission(android.Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {
                        @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                             file= Uri.fromFile(getOutputMediaFile());
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                            startActivityForResult(intent,0);

                        }
                        @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                        @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                    }).check();


//                PermissionListener dialogPermissionListener =
//                        DialogOnDeniedPermissionListener.Builder
//                                .withContext(context)
//                                .withTitle("Camera permission")
//                                .withMessage("Camera permission is needed to take pictures of your cat")
//                                .withButtonText("upload image")
//
//                                .build();
//                dialogPermissionListener.onPermissionGranted(new );

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                //galleryIntent.setAction();

                // Chooser of file system options.
                final Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose Image from Gallery");
                startActivityForResult(chooserIntent, 1010);


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nametext =  name.getText().toString();
                final String useremailET = email.getText().toString();
                final String passwordET = password.getText().toString();

                Log.d("name", nametext);
                if (!(TextUtils.isEmpty(useremailET) || TextUtils.isEmpty(passwordET))) {
                    mAuth.createUserWithEmailAndPassword(useremailET, passwordET).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {

                            Snackbar snackbar = Snackbar.make(signup, USER_CREATION_ERROR, Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        } else {

                            Snackbar snackbar = Snackbar.make(signup, USER_CREATION_SUCCESS, Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            uploadImage(imagePath,nametext,useremailET);

                        }
                        }
                    });

                }

                }
        });


    }


    /**
     * Upload URL of your folder with php file name...
     * You will find this file in php_upload folder in this project
     * You can copy that folder and paste in your htdocs folder...
     */




    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }
    public  JSONObject uploadImage(String sourceImageFile,String name,String email) {
        final String URL_UPLOAD_IMAGE = "http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Doctors/signUpDoctors";
        Toast.makeText(getApplicationContext(),sourceImageFile,Toast.LENGTH_LONG).show();


            File sourceFile = new File(sourceImageFile);

            Log.d("TAG", "File...::::" + sourceFile + " : " + sourceFile.exists());

            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

            String filename = sourceImageFile.substring(sourceImageFile.lastIndexOf("/")+1);




            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("username",name)
                    .addFormDataPart("email",email)
                    .addFormDataPart("address",address.getText().toString())
                    .addFormDataPart("phone",phone.getText().toString())
                    .addFormDataPart("specialization",specialization.getText().toString())
                    .addFormDataPart("result", "my_image")
                    .addFormDataPart("uploaded_file", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(URL_UPLOAD_IMAGE)
                    .post(requestBody)
                    .build();


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("response", e.toString());
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final okhttp3.Response response) throws IOException {

                    Log.d("response", response.toString());
                }
            });



        return null;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1010) {
            if (data == null) {
                Snackbar.make(findViewById(R.id.nonpi), "Unable to pick Image", Snackbar.LENGTH_INDEFINITE).show();
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
               Toast.makeText(getApplicationContext(),imagePath,Toast.LENGTH_LONG).show();
                cursor.close();

            }
            else
                Toast.makeText(getApplicationContext(),"unable to upload image",Toast.LENGTH_LONG).show();

        }

        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data == null) {
                Snackbar.make(findViewById(R.id.nonpi), "Unable to pick Image", Snackbar.LENGTH_INDEFINITE).show();
                return;
            }
            String imagestr= String.valueOf(file);
            imagePath=imagestr.substring(7);

            cameraclick=true;

            System.out.print("fileprani"+imagePath);
         //   Uri selectedImageUri = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getContentResolver().query(file, filePathColumn, null, null, null);
//
//            if (cursor != null) {
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//              imagePath = cursor.getString(columnIndex);
//
//                // Toast.makeText(getApplicationContext(),imagePath,Toast.LENGTH_LONG).show();
//                cursor.close();
//
//            }
//            else
//                Toast.makeText(getApplicationContext(),"unable to upload image",Toast.LENGTH_LONG).show();

        }
    }
}
