package example.kira.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


import java.util.ArrayList;
import java.util.Iterator;


public class InstructorAreaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button bGenerateVCode;
    ArrayList<String> cIDList;
    ArrayList<String> cNameList;
    final ArrayList<String> aIDList = new ArrayList<>();
    Button bCheck;
    final String verificationCode="xxaabb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_area);

        final TextView welcomeMessage=findViewById(R.id.tvWelcomeMsg);
        final Spinner spClass = findViewById(R.id.spClass);
        bCheck=findViewById(R.id.bCheck);
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
        final int index=i;
        Intent intent=getIntent();
        cIDList=intent.getStringArrayListExtra("cIDList");
        cNameList=intent.getStringArrayListExtra("cNameList");



        Toast.makeText(this,"You selected "+myText.getText(), Toast.LENGTH_SHORT).show();
        bGenerateVCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(InstructorAreaActivity.this);
                                builder.setMessage("You code is generated: "+verificationCode)
                                        .setNegativeButton("Back",null)
                                        .create()
                                        .show();
                            }else{
                                String vCodeGenerated=jsonResponse.getString("code");
                                AlertDialog.Builder builder = new AlertDialog.Builder(InstructorAreaActivity.this);
                                builder.setMessage("Your code has already been generated: "+vCodeGenerated)
                                        .setNegativeButton("Back",null)
                                        .create()
                                        .show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            System.out.println(response);
                        }
                    }
                };
                InstructorAreaRequest instructorAreaRequest = new InstructorAreaRequest(cIDList.get(index),verificationCode, responseListener);
                RequestQueue queue = Volley.newRequestQueue(InstructorAreaActivity.this);
                queue.add(instructorAreaRequest);
            }
        });

        bCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        try {
//                            JSONObject json=new JSONObject(response);
//                            JSONObject jsonResponse = new JSONObject(json.getString("response"));
//                            boolean success = jsonResponse.getBoolean("success");
//                            if(success) {
//                                JSONObject jsonAID = new JSONObject(json.getString("aID"));
//                                convertJSONObjectToArrary(jsonAID);
                                Intent intent = new Intent(InstructorAreaActivity.this, InstructorAttendActivity.class);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("cIDa", cIDList.get(index));
                                //TODO 获取GPS并传入intent.putExtra
                                InstructorAreaActivity.this.startActivity(intent);
//                            }
//                        }catch(JSONException e){
//                            e.printStackTrace();
//                            System.out.println(response);
//                        }
                    }
                    };

                    InstructorAttendRequest instructorAttendRequest = new InstructorAttendRequest(cIDList.get(index), verificationCode, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(InstructorAreaActivity.this);
                queue.add(instructorAttendRequest);
            }
        });

    }

        @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    protected void convertJSONObjectToArrary(JSONObject aID) {
        Iterator x = aID.keys();
        aIDList.clear();
        try {
            while (x.hasNext()) {
                String key = (String) x.next();
                //System.out.println(aID.get(key).toString());
                aIDList.add(aID.get(key).toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
