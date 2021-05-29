package components.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.AuthRequest;

import javax.ws.rs.core.UriBuilder;
import java.net.http.HttpRequest;
import java.util.Map;

public class HttpRequestBuilder {

    public static final String BASE_URL = "";
    public static final String AUTH_SIGNUP_ENDPOINT = "/auth/signup";
    public static final String AUTH_LOGIN_ENDPOINT = "/auth/login";
    public static final String RESUPPLY_ENDPOINT = "/resupply";

    public static HttpRequest buildResupplyGET(Map<String, String> nameValuePairs) {

        return HttpRequest.newBuilder()
                .uri(parseURI(RESUPPLY_ENDPOINT, nameValuePairs).build())
                .header("Accept", "application/json")
                .GET()
                .build();
    }

    private static UriBuilder parseURI(String endpoint, Map<String, String> paramValuePairs){

        UriBuilder builder = parseURI(endpoint);

        for(String key : paramValuePairs.keySet()){
            builder.queryParam(key, paramValuePairs.get(key));
        }

        return builder;
    }

    private static UriBuilder parseURI(String endpoint){
        return UriBuilder.fromUri(String.format("%s%s", BASE_URL, endpoint));
    }

    public static HttpRequest buildSignupPOST(AuthRequest authRequest) throws JsonProcessingException {
        return buildPOST(authRequest, AUTH_SIGNUP_ENDPOINT);
    }

    public static HttpRequest buildLoginPOST(AuthRequest authRequest) throws JsonProcessingException {
        return buildPOST(authRequest, AUTH_LOGIN_ENDPOINT);
    }

    private static HttpRequest buildPOST(AuthRequest authRequest, String endpoint) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(authRequest);

        return HttpRequest.newBuilder()
                .uri(parseURI(endpoint).build())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }
}