package example.kira.loginregister;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class StudentAreaRequest extends StringRequest {
    private static final String STUDENT_AREA_REQUEST_URL="http://xzhou26.000webhostapp.com/StudentArea.php";
    private Map<String, String> params;

    public StudentAreaRequest(String studentID, String classID, String verificationCode, String langtitude, String longtitude, Response.Listener<String> listener){
        super(Request.Method.POST,STUDENT_AREA_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("sID",studentID);
        params.put("cID",classID);
        params.put("verificationCode",verificationCode);
        params.put("langtitude", langtitude);
        params.put("longtitude",longtitude);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
