package example.kira.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    final ArrayList<String> cIDList=new ArrayList<>();
    final ArrayList<String> cNameList=new ArrayList<>();
    final ArrayList<String> cList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etID =  findViewById(R.id.etID);
        final EditText etPassword =  findViewById(R.id.etPassword);
        final Button bLogin =  findViewById(R.id.bLogin);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ID = etID.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONObject jsonResponse = new JSONObject(json.getString("response"));
                            JSONObject jsonClassList = new JSONObject(json.getString("response2"));
                            JSONObject jsonClassID=new JSONObject(json.getString("cID"));
                            JSONObject jsonClassName=new JSONObject(json.getString("cName"));
                            boolean Ssuccess = jsonResponse.getBoolean("Ssuccess");
                            boolean Isuccess = jsonResponse.getBoolean("Isuccess");

                            if (Ssuccess) {
                                String name = jsonResponse.getString("Name");
                                String sID=jsonResponse.getString("ID");
                                System.out.println(json.toString());
                                convertJSONObjectToArrary(jsonClassList,jsonClassID,jsonClassName);

                                Intent intent = new Intent(LoginActivity.this, StudentAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("cList", cList);
                                intent.putExtra("cIDList",cIDList);
                                intent.putExtra("cNameList",cNameList);
                                intent.putExtra("sID",sID);
                                //TODO 获取GPS并传入intent.putExtra
                                LoginActivity.this.startActivity(intent);
                            } else if(Isuccess){
                                String name = jsonResponse.getString("Name");
                                System.out.println(json.toString());
                                convertJSONObjectToArrary(jsonClassList,jsonClassID,jsonClassName);

                                Intent intent = new Intent(LoginActivity.this, InstructorAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("cList", cList);
                                intent.putExtra("cIDList",cIDList);
                                intent.putExtra("cNameList",cNameList);

                                LoginActivity.this.startActivity(intent);
                            } else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(response);
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(ID, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    protected void convertJSONObjectToArrary(JSONObject jsonObject,JSONObject cID,JSONObject cName) {
        Iterator x = jsonObject.keys();
        cList.clear();
        cIDList.clear();
        cNameList.clear();
        try {
            while (x.hasNext()) {
                String key = (String) x.next();
                System.out.println(cID.get(key).toString());
                System.out.println(cName.get(key).toString());
                cIDList.add(cID.get(key).toString());
                cNameList.add(cName.get(key).toString());
                cList.add(cID.get(key).toString()+" "+cName.get(key).toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
