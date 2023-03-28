package security.rand.config;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ContextHolder {

    private static final ThreadLocal<Map<String, Object>> userContext = ThreadLocal.withInitial(HashMap::new);
    public static final String USER_CONTEXT = "USER_CONTEXT";
    public static final String CORPORATE_ID = "CORPORATE_ID";
    public static final String SERVICE_TAG = "SERVICE_TAG";



    public  void put(JwtAuthenticationToken jwt){
            String username = (String) jwt.getToken().getClaims().get("preferred_username");
            Context context = new Context(username, jwt.getToken().getTokenValue());
            userContext.get().put(USER_CONTEXT, context);

    }

    public void putCorporateId(String corporateId){
        userContext.get().put(CORPORATE_ID, corporateId);
    }
    public void putServiceTag(String serviceTag){
        userContext.get().put(SERVICE_TAG, serviceTag);
    }


    public static Context get(){
        return (Context) userContext.get().get(USER_CONTEXT);
    }

    public static String getCorporateId() {
        return (String)userContext.get().get(CORPORATE_ID);
    }
    public static String getServiceTag() {
        return (String)userContext.get().get(SERVICE_TAG);
    }
    public static void clearContext() {
        userContext.remove();
    }
}
