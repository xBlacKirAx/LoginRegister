package example.kira.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstructorAreaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button bGenerateVCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_area);

        final TextView welcomeMessage=findViewById(R.id.tvWelcomeMsg);
        final Spinner spClass = findViewById(R.id.spClass);
        final Button bCheck= findViewById(R.id.bSign);
        bGenerateVCode=findViewById(R.id.bGenerateVCode);



        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        ArrayList<String> cList=intent.getStringArrayListExtra("cList");
        ArrayAdapter<String> adapater=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,cList);

        spClass.setAdapter(adapater);
        spClass.setOnItemSelectedListener(this);
        String message=name+", you are logged in as instructor";
        welcomeMessage.setText(message);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView myText=(TextView) view;
        System.out.println(i);
        Toast.makeText(this,"You selected "+myText.getText(), Toast.LENGTH_SHORT).show();
        bGenerateVCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                final String CID=" ";
                final String verificationCode=" ";
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(InstructorAreaActivity.this);
                                builder.setMessage("You are signed in!")
                                        .setNegativeButton("Back",null)
                                        .create()
                                        .show();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(InstructorAreaActivity.this);
                                builder.setMessage("There are something wrong, please try again.")
                                        .setNegativeButton("Back",null)
                                        .create()
                                        .show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                InstructorAreaRequest instructorAreaRequest = new InstructorAreaRequest(CID,verificationCode, responseListener);
                RequestQueue queue = Volley.newRequestQueue(InstructorAreaActivity.this);
                queue.add(instructorAreaRequest);
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
