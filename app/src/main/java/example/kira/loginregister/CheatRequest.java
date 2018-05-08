package example.kira.loginregister;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheatRequest extends StringRequest {
    private static final String STUDENT_AREA_REQUEST_URL="http://xzhou26.000webhostapp.com/Cheat.php";
    private Map<String, String> params;

    public CheatRequest(String studentID, String classID, String cheatSID, Response.Listener<String> listener){
        super(Request.Method.POST,STUDENT_AREA_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("sID",studentID);
        params.put("cID",classID);
        params.put("sID2",cheatSID);
        params.put("cID2",classID);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
