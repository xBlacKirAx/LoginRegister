package example.kira.loginregister;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudentAreaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button bSign;
    ArrayList<String> cIDList;
    ArrayList<String> cNameList;
    double latitude;
    double longtitude;
    private BroadcastReceiver broadcastReceiver;
    private String fileName="file.txt";
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date date = new Date();
    String currentDate=dateFormat.format(date);

//    Button bTemp;
    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    latitude = (double) intent.getExtras().get("latitude");
                    longtitude = (double) intent.getExtras().get("longtitude");
                    Toast.makeText(context, "latitude: " + latitude, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "longtitude: " + longtitude, Toast.LENGTH_SHORT).show();
                    System.out.println("latitude: " + latitude);
                    System.out.println("longtitude: " + longtitude);
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_area);

        final TextView welcomeMessage = findViewById(R.id.tvWelcomeMsg);
        final Spinner spClass = findViewById(R.id.spClass);
        bSign = findViewById(R.id.bSign);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        ArrayList<String> cList = intent.getStringArrayListExtra("cList");
        ArrayAdapter<String> adapater = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cList);
        spClass.setAdapter(adapater);
        spClass.setOnItemSelectedListener(this);
        String message = name + ", you are logged in as student";
        welcomeMessage.setText(message);
        if (!runtime_permissions()) {
            Intent gpsIntent = new Intent(getApplicationContext(), GPS_Service.class);
            startService(gpsIntent);

        }

//        bTemp=findViewById(R.id.bTemp);
//        bTemp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clearFile();
//                System.out.println("file: "+readFile(fileName)+"end");
//            }
//        });


    }

    private boolean runtime_permissions() {
        System.out.println(Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView myText = (TextView) view;
        System.out.println(i);
        Toast.makeText(this, "You selected " + myText.getText(), Toast.LENGTH_SHORT).show();

        if (!runtime_permissions()) {
            Intent gpsIntent = new Intent(getApplicationContext(), GPS_Service.class);
            startService(gpsIntent);
            enable_signin(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Intent gpsIntent = new Intent(getApplicationContext(), GPS_Service.class);
                startService(gpsIntent);
            } else {
                runtime_permissions();
            }
        }
    }

    private void enable_signin(int i) {
        final int index = i;
        Intent intent = getIntent();
        cIDList = intent.getStringArrayListExtra("cIDList");
        cNameList = intent.getStringArrayListExtra("cNameList");
        final String sID = intent.getStringExtra("sID");


        bSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentAreaActivity.this);
                LayoutInflater inflater = StudentAreaActivity.this.getLayoutInflater();
                final View rootView = inflater.inflate(R.layout.dialog_student_enter_vcode, null);


                builder.setView(rootView)
                        .setPositiveButton("Sign in!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent gpsIntent = new Intent(getApplicationContext(), GPS_Service.class);
                                startService(gpsIntent);
                                final EditText verificationCode = rootView.findViewById(R.id.verificationCode);
                                final String vCode = verificationCode.getText().toString();




                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            String errorResponse = jsonResponse.getString("response");

                                            AlertDialog.Builder builder = new AlertDialog.Builder(StudentAreaActivity.this);
                                            builder.setMessage(errorResponse)
                                                    .setNegativeButton("Back", null)
                                                    .create()
                                                    .show();
                                            if(jsonResponse.getBoolean("success")){

                                                if(readFile(fileName).contains(cIDList.get(index)+currentDate+sID)){
                                                    System.out.println(readFile(fileName).substring(readFile(fileName).lastIndexOf(currentDate)+10,readFile(fileName).lastIndexOf(currentDate)+16));
                                                    String cheatSID=readFile(fileName).substring(readFile(fileName).lastIndexOf(currentDate)+10,readFile(fileName).lastIndexOf(currentDate)+16);
                                                    Response.Listener<String> cheatListener=new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String cheatResponse) {
                                                            try{
                                                                JSONObject jsonCheatResponse = new JSONObject(cheatResponse);
                                                                boolean errorResponse = jsonCheatResponse.getBoolean("success");
                                                                System.out.println(errorResponse);
                                                            }catch (JSONException e){
                                                                e.printStackTrace();
                                                                System.out.println(cheatResponse);
                                                            }

                                                        }
                                                    };
                                                    CheatRequest CheatRequest =new CheatRequest(sID, cIDList.get(index),cheatSID,cheatListener);
                                                    RequestQueue queue = Volley.newRequestQueue(StudentAreaActivity.this);
                                                    queue.add(CheatRequest);
                                                }else{
                                                    //正常sign in
                                                    System.out.println("正常sign in");
                                                    saveFile(fileName,cIDList.get(index)+currentDate+sID+"\n");
                                                }

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            System.out.println(response);
                                        }
                                    }
                                };

                                StudentAreaRequest studentAreaRequest = new StudentAreaRequest(sID, cIDList.get(index), vCode, Double.toString(latitude), Double.toString(longtitude), responseListener);
                                System.out.println(latitude);
                                System.out.println(longtitude);
                                RequestQueue queue = Volley.newRequestQueue(StudentAreaActivity.this);
                                queue.add(studentAreaRequest);


//                                stopService(gpsIntent);
                            }
                        })
                        .setNegativeButton("Back", null)
                        .setTitle(cIDList.get(index) + " " + cNameList.get(index))
                        .create()
                        .show();
            }
        });
    }
    public void clearFile(){
        try{
            FileOutputStream fos=openFileOutput(fileName,Context.MODE_PRIVATE);
            fos.write('\n');
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void saveFile(String file, String text){
        try{
            FileOutputStream fos=openFileOutput(file, Context.MODE_APPEND);
            fos.write(text.getBytes());

            fos.close();
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String readFile(String file){
        String text="";
        try{
            FileInputStream fis=openFileInput(file);
            int size=fis.available();
            byte[] buffer=new byte[size];
            fis.read(buffer);
            fis.close();
            text =new String(buffer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return text;
    }

}
