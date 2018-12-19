package android.rafid.gstore;

import android.app.ProgressDialog;
import android.rafid.gstore.Model.User;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.rafid.gstore.R;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText Name,Phone,Password,Email;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Name = (MaterialEditText) findViewById(R.id.Name);
        Phone = (MaterialEditText) findViewById(R.id.Phone);
        Password = (MaterialEditText) findViewById(R.id.Password);
        Email = (MaterialEditText) findViewById(R.id.Email);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(Phone.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this,"Username already exist",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            mDialog.dismiss();
                            User user = new User(Name.getText().toString(),Password.getText().toString(),Email.getText().toString());
                            table_user.child(Phone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this,"Sign Up Successfully!",Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });






    }
}
