package example.kira.loginregister;

import android.widget.GridLayout.LayoutParams;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class InstructorAttendActivity extends AppCompatActivity {

    Button back;
//    Button current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
//        back = findViewById(R.id.back);
//        current = findViewById(R.id.Current);
        final TextView attend = findViewById(R.id.attend);

        Intent intent = getIntent();
        String cID = intent.getStringExtra("cIDa");
//        ArrayList<String> aID = intent.getStringArrayListExtra("aID");
//        attend.setText("");
        attend.setText(cID);
//        for (int j = aID.size() -1 ; j >= 0; j--){
//           attend.append( aID.get(j) + "\n");
    }
}

//    current.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//            Response.Listener<String> responseListener = new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                        try {
//                            JSONObject json=new JSONObject(response);
//                            JSONObject jsonResponse = new JSONObject(json.getString("response"));
//                            boolean success = jsonResponse.getBoolean("success");
//                            if(success) {
//                                JSONObject jsonAID = new JSONObject(json.getString("aID"));
//                                convertJSONObjectToArrary(jsonAID);
//                    Intent intent = new Intent(InstructorAreaActivity.this, InstructorAttendActivity.class);
//                    //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("cIDa", cIDList.get(index));
//                    //TODO 获取GPS并传入intent.putExtra
//                    InstructorAreaActivity.this.startActivity(intent);
////                            }
////                        }catch(JSONException e){
////                            e.printStackTrace();
////                            System.out.println(response);
////                        }
//                }
//            };
//
//}
//
//
