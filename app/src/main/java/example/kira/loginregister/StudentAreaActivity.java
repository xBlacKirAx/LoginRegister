package example.kira.loginregister;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class StudentAreaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button bSign;
    ArrayList<String> cIDList;
    ArrayList<String> cNameList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_area);

        final TextView welcomeMessage=findViewById(R.id.tvWelcomeMsg);
        final Spinner spClass =findViewById(R.id.spClass);
        bSign=findViewById(R.id.bSign);



        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        ArrayList<String> cList=intent.getStringArrayListExtra("cList");
        ArrayAdapter<String> adapater=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,cList);
        spClass.setAdapter(adapater);
        spClass.setOnItemSelectedListener(this);
        String message=name+", you are logged in as student";
        welcomeMessage.setText(message);




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView myText=(TextView) view;
        System.out.println(i);
        Toast.makeText(this,"You selected "+myText.getText(), Toast.LENGTH_SHORT).show();
        final int index=i;
        Intent intent=getIntent();
        cIDList=intent.getStringArrayListExtra("cIDList");
        cNameList=intent.getStringArrayListExtra("cNameList");
        final String sID=intent.getStringExtra("sID");

        bSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(StudentAreaActivity.this);
                LayoutInflater inflater=StudentAreaActivity.this.getLayoutInflater();
                final View rootView=inflater.inflate(R.layout.dialog_student_enter_vcode,null);
                builder.setView(rootView)
                        .setPositiveButton("Sign in!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                final EditText verificationCode=rootView.findViewById(R.id.verificationCode);
                                final String vCode=verificationCode.getText().toString();
                                System.out.println(sID+" "+cIDList.get(index));
                                System.out.println(vCode);

                                Response.Listener<String> responseListener=new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try{
                                            JSONObject jsonResponse=new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            String errorResponse=jsonResponse.getString("response");
                                            if(success){
                                                AlertDialog.Builder builder = new AlertDialog.Builder(StudentAreaActivity.this);
                                                builder.setMessage("You are signed in!")
                                                        .setNegativeButton("Back",null)
                                                        .create()
                                                        .show();
                                            }else{
                                                AlertDialog.Builder builder = new AlertDialog.Builder(StudentAreaActivity.this);
                                                builder.setMessage(errorResponse)
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
                                StudentAreaRequest studentAreaRequest = new StudentAreaRequest(sID,cIDList.get(index),vCode, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(StudentAreaActivity.this);
                                queue.add(studentAreaRequest);
                            }
                        })
                        .setNegativeButton("Back",null)
                        .setTitle(cIDList.get(index)+" "+cNameList.get(index))
                        .create()
                        .show();


            }
        });




//        AlertDialog.Builder builder = new AlertDialog.Builder(StudentAreaActivity.this);
//        builder.setMessage("Do you want to sign in this class?")
//                .setPositiveButton("Yes",null)
//                .setNegativeButton("No", null)
//                .create()
//                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
