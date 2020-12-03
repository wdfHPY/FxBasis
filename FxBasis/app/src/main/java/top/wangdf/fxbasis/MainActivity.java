package top.wangdf.fxbasis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import top.wangdf.fxbasis.common.Utils;
import top.wangdf.fxbasis.entity.VestEntity;
import top.wangdf.fxbasis.net.VestApi;
import top.wangdf.fxbasis.services.FireMessagingService;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {


    private static final String vestCode = "Q11KF9MU";

    private static final String version = "1.0";

    private static final int RC_SIGN_IN = 1001;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VestApi api = new VestApi();
//        startService(new Intent(this, FireMessagingService.class));


        //请求接口
        //api.makeVestRequest(new VestEntity("Q11KF9MU","1.0", Utils.getDeviceId(MainActivity.this), Utils.getTimeStamp(),"google"));
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
        /**
         * <resources>
         *     <string name="firebase_database_url" translatable="false">https://fxbasisone-1606737618140.firebaseio.com</string>
         *     <string name="gcm_defaultSenderId" translatable="false">393053980436</string>
         *     <string name="google_api_key" translatable="false">AIzaSyAe0nozTGKBPLdKgs7PIudDJLW-mL52snc</string>
         *     <string name="google_app_id" translatable="false">1:393053980436:android:f72f576f60d416dff36401</string>
         *     <string name="google_crash_reporting_api_key" translatable="false">AIzaSyAe0nozTGKBPLdKgs7PIudDJLW-mL52snc</string>
         *     <string name="google_storage_bucket" translatable="false">fxbasisone-1606737618140.appspot.com</string>
         *     <string name="project_id" translatable="false">fxbasisone-1606737618140</string>
         * </resources>
         */
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:820777635006:android:8e29918175bde636c8e2ef") // Required for Analytics.
                .setProjectId("fxbasis-820777635006") // Required for Firebase Installations.
                .setApiKey("AIzaSyD4p5id4CDHHbh3NFtEc4LYa1OquvnpP6E")
                .build();
        FirebaseApp.initializeApp(this, options, "FxBasis");
//        //Toast.makeText(this, FirebaseMessaging.getInstance().getToken().getResult()+"", Toast.LENGTH_SHORT).show();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();
                    Log.d(TAG, token);
                    Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.i(TAG, "onActivityResult: " + account.getId());
                Log.i(TAG, "onActivityResult: " + account.getDisplayName());
                Log.i(TAG, "onActivityResult: " + account.getEmail());
                //{"sign":"781h18fn1u34n","host":"https://bb.skr.today"}
                /**
                 * 成功获取到用户Google登录的信息
                 */
                //进行三方请求
            } catch (ApiException ignored) {
                Log.i(TAG, "onActivityResult: " + ignored.getStatusCode());
            }
        }
    }
}