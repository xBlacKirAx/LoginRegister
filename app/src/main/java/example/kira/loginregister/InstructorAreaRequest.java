package example.kira.loginregister;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class InstructorAreaRequest extends StringRequest {
    private static final String INSTRUCTOR_AREA_REQUEST_URL="http://xzhou26.000webhostapp.com/InstructorArea.php";
    private Map<String, String> params;
    public InstructorAreaRequest(String classID, String verificationCode, Response.Listener<String> listener){
        super(Request.Method.POST,INSTRUCTOR_AREA_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("classID",classID);
        params.put("verificationCode",verificationCode);
    }
}

