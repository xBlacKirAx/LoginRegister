package example.kira.loginregister;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class InstructorGetSpRequest extends StringRequest {
    private static final String INSTRUCTOR_Attend_REQUEST_URL="http://xzhou26.000webhostapp.com/GetAttendSp.php";
    private Map<String, String> params;
    public InstructorGetSpRequest(String cID, String date,  Response.Listener<String> listener){
        super(Request.Method.POST,INSTRUCTOR_Attend_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("cID",cID);
        params.put("spDate",date);
        

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

