package example.kira.loginregister;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kira on 4/14/2018.
 */

class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL="http://xzhou26.000webhostapp.com/Login2.php";
    private Map<String, String> params;
    public LoginRequest(String ID, String password, Response.Listener<String> listener){
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("SID",ID);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
