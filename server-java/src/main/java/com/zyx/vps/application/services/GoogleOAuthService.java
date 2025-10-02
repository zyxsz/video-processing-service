package com.zyx.vps.application.services;

import java.io.IOException;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.zyx.vps.application.dto.auth.GoogleOAuthCallbackDTO;
import com.zyx.vps.application.dto.auth.ProvidedUserProfile;
import com.zyx.vps.application.errors.NotfoundException;

public class GoogleOAuthService {

  private String OAuthGoogleRedirectUri = "http://localhost:3000/authentication/google/callback";
  private String OAuthGoogleAuthEndpoint = "https://accounts.google.com/o/oauth2/v2/auth";
  private String OAuthGoogleTokenEndpoint = "https://oauth2.googleapis.com/token";
  private String OAuthGoogleClientId = "ci";
  private String OAuthGoogleClientSecret = "cs";
  private String OAuthGoogleProfileEndpoint = "https://www.googleapis.com/oauth2/v1/userinfo";

  private HttpClient httpClient = HttpClients.createDefault();
  // private ApiTokenRepository apiTokenRepository;
  // private UsersRepository usersRepository;

  public String redirect() {
    StringBuilder redirectUrl = new StringBuilder(OAuthGoogleAuthEndpoint);

    redirectUrl.append("?");
    redirectUrl.append("client_id=" + OAuthGoogleClientId);
    redirectUrl.append("&redirect_uri=" + OAuthGoogleRedirectUri);
    redirectUrl.append("&response_type=code");
    redirectUrl.append(
        "&scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile+openid");
    redirectUrl.append("&include_granted_scopes=true");
    redirectUrl.append("&prompt=consent");

    return redirectUrl.toString();
  }

  public GoogleOAuthCallbackDTO callback(String code) throws IOException {
    StringBuilder tokenUrl = new StringBuilder(OAuthGoogleTokenEndpoint);

    tokenUrl.append("?");
    tokenUrl.append("code=" + code);
    tokenUrl.append("&client_id=" + OAuthGoogleClientId);
    tokenUrl.append(
        "&client_secret=" + OAuthGoogleClientSecret);
    tokenUrl.append("&redirect_uri=" + OAuthGoogleRedirectUri);
    tokenUrl.append("&grant_type=authorization_code");

    HttpPost httpPost = new HttpPost(tokenUrl.toString());

    HttpResponse response = this.httpClient.execute(httpPost);
    String responseString = EntityUtils.toString(response.getEntity());

    JSONObject responseBody = new JSONObject(responseString);

    Boolean isError = responseBody.has("error");

    if (isError)
      throw new NotfoundException("Error on provider");

    String tokenType = (String) responseBody.get("token_type");
    String accessToken = (String) responseBody.get("access_token");
    String scope = (String) responseBody.get("scope");
    String idToken = (String) responseBody.get("id_token");
    Integer expiresIn = (Integer) responseBody.get("expires_in");

    GoogleOAuthCallbackDTO data = new GoogleOAuthCallbackDTO(idToken, accessToken, tokenType, scope, expiresIn);

    return data;
  }

  public ProvidedUserProfile getUserProfile(String accessToken) throws ClientProtocolException, IOException {
    StringBuilder requestUrl = new StringBuilder(OAuthGoogleProfileEndpoint);

    HttpGet request = new HttpGet(requestUrl.toString());

    request.setHeader("Authorization", "Bearer " + accessToken);

    HttpResponse response = this.httpClient.execute(request);
    String responseString = EntityUtils.toString(response.getEntity());

    JSONObject responseBody = new JSONObject(responseString);

    String id = responseBody.getString("id");
    String email = responseBody.getString("email");
    Boolean isVerified = responseBody.getBoolean("verified_email");
    String givenName = responseBody.getString("given_name");
    String picture = responseBody.getString("picture");
    String username = responseBody.getString("name");

    ProvidedUserProfile profile = new ProvidedUserProfile(id, email, isVerified, username, givenName,
        picture);

    return profile;
  }

}
