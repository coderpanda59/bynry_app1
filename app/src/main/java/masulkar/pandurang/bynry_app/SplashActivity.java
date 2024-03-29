    package masulkar.pandurang.bynry_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

    public class SplashActivity extends AppCompatActivity {

        //firebase auth
        private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //init firebasse auth
        firebaseAuth = FirebaseAuth.getInstance();

        //start main screen after 2 seconds
        new Handler().postDelayed(new Runnable(){
            public void run(){
            checkUser();
            }
        },2000);//2000 means 2 sec
    }

        private void checkUser() {
        // get current user , if logged in
            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
            if(firebaseUser==null){
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
            else{
                //user logged in check user type ,same as done in login screen

                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
                ref.child(firebaseUser.getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                //get user type
                                String usertype=""+snapshot.child("userType").getValue();
                                //check user type
                                if (usertype.equals("user"))
                                {
                                    //this is simple user ,open user dashbord
                                    startActivity(new Intent(SplashActivity.this,DashbordUserActivity.class));
                                    finish();
                                }
                                else if (usertype.equals("admin")) {
                                    //this is admin  ,open admin dashbord
                                    startActivity(new Intent(SplashActivity.this,DashbordAdminActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled( DatabaseError error) {

                            }
                        });
            }
        }
    }