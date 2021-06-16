package components.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.logic.Preference;
import dto.AuthRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.time.LocalDate;

public class HttpRequestBuilder {

    public static final String BASE_URL = "http://localhost:8080/resuplify";
    public static final String AUTH_SIGNUP_ENDPOINT = "/auth/signup";
    public static final String AUTH_LOGIN_ENDPOINT = "/auth/login";
    public static final String RESUPPLY_ENDPOINT = "/resupply";

    public static HttpRequest buildResupplyGET(LocalDate date) throws URISyntaxException {

        return HttpRequest.newBuilder()
                .uri(parseURI(RESUPPLY_ENDPOINT, date))
                .header("Accept", "application/json")
                .header("Authorization", String.format("Bearer %s", Preference.getToken()))
                .GET()
                .build();
    }

    private static URI parseURI(String endpoint, LocalDate date) throws URISyntaxException {

        URI uri = parseURI(endpoint);
        return new URI(String.format("%s?%s=%s", uri.toString(), "date", date));
    }

    private static URI parseURI(String endpoint) throws URISyntaxException {
        return new URI(String.format("%s%s", BASE_URL, endpoint));
    }

    public static HttpRequest buildSignupPOST(AuthRequest authRequest) throws JsonProcessingException, URISyntaxException {
        return buildPOST(authRequest, AUTH_SIGNUP_ENDPOINT);
    }

    public static HttpRequest buildLoginPOST(AuthRequest authRequest) throws JsonProcessingException, URISyntaxException {
        return buildPOST(authRequest, AUTH_LOGIN_ENDPOINT);
    }

    private static HttpRequest buildPOST(AuthRequest authRequest, String endpoint) throws JsonProcessingException, URISyntaxException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter();
        String body = mapper.writeValueAsString(authRequest);

        return HttpRequest.newBuilder()
                .uri(parseURI(endpoint))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }
}