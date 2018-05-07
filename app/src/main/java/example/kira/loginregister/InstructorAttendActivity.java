package example.kira.loginregister;

import android.widget.AdapterView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.Iterator;

public class InstructorAttendActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String verificationCode = "xxaabb";
    Button back;
    Button current;
    String cID = "";
    ArrayList<String> attendList = new ArrayList<>();
    ArrayList<String> dateList=new ArrayList<>();
    TextView attend;
    String date = "";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        back = findViewById(R.id.back);
        current = findViewById(R.id.Current);

        final Spinner spDate = findViewById(R.id.spDate);
        attend = findViewById(R.id.attend);
        Intent intent = getIntent();
        cID = intent.getStringExtra("cIDa");
        dateList=intent.getStringArrayListExtra("dateList");
        ArrayAdapter<String> ada=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,dateList);
        spDate.setAdapter(ada);
        spDate.setOnItemSelectedListener(this);

        attend.setText("Please tap Current to show the attendance for today's class or select a date and tap show.");
        current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String currentDate=dateList.get(dateList.size()-1);
                    attend.setText("");
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json = new JSONObject(response);
                                JSONObject jsonResponse = new JSONObject(json.getString("response"));
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    JSONObject jsonAID = new JSONObject(json.getString("aID"));
                                    convertJSONObjectToArrary(jsonAID);
                                    attend.append(currentDate+"\n");
                                    for (int j = attendList.size() - 1; j >= 0; j--) {
                                        attend.append(attendList.get(j) + "\n");
                                    }
                                    System.out.print(attend);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(response);
                            }
                        }
                    };
                    InstructorGetAttendanceRequest instructorGetAttendanceRequest = new InstructorGetAttendanceRequest(cID, currentDate, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(InstructorAttendActivity.this);
                    queue.add(instructorGetAttendanceRequest);
                }
            });

        }
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView myText=(TextView) view;
                System.out.println(i);
                date = dateList.get(i);
                Toast.makeText(adapterView.getContext(),myText.getText(), Toast.LENGTH_SHORT).show();
                back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attend.setText("");
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json = new JSONObject(response);
                                JSONObject jsonResponse = new JSONObject(json.getString("response"));
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    JSONObject jsonAID = new JSONObject(json.getString("aID"));
                                    convertJSONObjectToArrary(jsonAID);
                                    attend.append(date+"\n");
                                    for (int j = attendList.size() - 1; j >= 0; j--) {
                                        attend.append(attendList.get(j) + "\n");
                                    }
                                    System.out.print(attend);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(response);
                            }
                        }
                    };
                    InstructorGetAttendanceRequest instructorGetAttendanceRequest = new InstructorGetAttendanceRequest(cID, date, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(InstructorAttendActivity.this);
                    queue.add(instructorGetAttendanceRequest);
                }
            });
        }
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
        protected void convertJSONObjectToArrary (JSONObject aID){
            Iterator x = aID.keys();
            attendList.clear();
            try {
                while (x.hasNext()) {
                    String key = (String) x.next();
                    attendList.add(aID.get(key).toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }





}


