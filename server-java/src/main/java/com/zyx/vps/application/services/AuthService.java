package com.zyx.vps.application.services;

import java.io.File;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.ClientProtocolException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zyx.vps.application.dto.auth.ApiTokenDTO;
import com.zyx.vps.application.dto.auth.AuthVerifyTokenDTO;
import com.zyx.vps.application.dto.auth.GoogleOAuthCallbackDTO;
import com.zyx.vps.application.dto.auth.ProvidedUserProfile;
import com.zyx.vps.application.entities.ApiToken;
import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.entities.local.ApiTokenLocal;
import com.zyx.vps.application.entities.local.UserLocal;
import com.zyx.vps.application.enums.OAuthProvider;
import com.zyx.vps.application.errors.BadRequestException;
import com.zyx.vps.application.errors.UnauthorizedException;
import com.zyx.vps.application.repositories.ApiTokenRepository;
import com.zyx.vps.application.repositories.UsersRepository;
import com.zyx.vps.application.utils.RSA;

public class AuthService {

  private String JWTPrivateKeyPath = "src/main/resources/keys/jwt-private.pem";
  private String JWTPublicKeyPath = "src/main/resources/keys/jwt-public.pem";

  private RSAPublicKey JWTRSAPublicKey;
  private RSAPrivateKey JWTRSAPrivateKey;

  private Algorithm JWTAlgorithm;

  private GoogleOAuthService googleOAuthService;
  private ApiTokenRepository apiTokenRepository;
  private UsersRepository usersRepository;

  public AuthService(GoogleOAuthService googleOAuthService, UsersRepository usersRepository,
      ApiTokenRepository apiTokenRepository) {
    this.googleOAuthService = googleOAuthService;
    this.usersRepository = usersRepository;
    this.apiTokenRepository = apiTokenRepository;

    try {
      this.JWTRSAPublicKey = RSA.readX509PublicKey(new File(this.JWTPublicKeyPath));
      this.JWTRSAPrivateKey = RSA.readPKCS8PrivateKey(new File(this.JWTPrivateKeyPath));
      this.JWTAlgorithm = Algorithm.RSA256(this.JWTRSAPublicKey, this.JWTRSAPrivateKey);
    } catch (Exception e) {
      // e.printStackTrace();
    }
  }

  public AuthVerifyTokenDTO verifyToken(String accessToken) throws Exception {
    JWTVerifier verifier = JWT.require(this.JWTAlgorithm)
        .build();

    DecodedJWT decodedJWT = verifier.verify(accessToken);

    String token = decodedJWT.getClaim("token").asString();

    if (token == null)
      throw new UnauthorizedException("Invalid token");

    ApiToken apiToken = this.apiTokenRepository.findByToken(token);

    if (apiToken == null)
      throw new UnauthorizedException("Invalid api token");

    if (!apiToken.isEnabled())
      throw new UnauthorizedException("Api token not enabled");

    if (apiToken.isExpired())
      throw new UnauthorizedException("Api token expired");

    User user = this.usersRepository.findById(apiToken.getUser().getId());

    AuthVerifyTokenDTO authVerifyTokenDto = new AuthVerifyTokenDTO(apiToken, user);

    return authVerifyTokenDto;
  }

  public String redirect(OAuthProvider provider) {
    if (provider == OAuthProvider.GOOGLE) {
      return this.googleOAuthService.redirect();
    }

    return null;
  }

  public ApiTokenDTO callback(OAuthProvider provider, String code) throws ClientProtocolException, IOException, Error {

    if (provider == OAuthProvider.GOOGLE) {
      GoogleOAuthCallbackDTO googleOAuthCallbackDTO = this.googleOAuthService.callback(code);
      ProvidedUserProfile profile = this.googleOAuthService.getUserProfile(googleOAuthCallbackDTO.getAccessToken());

      User user = this.usersRepository.findByEmail(profile.getEmail());

      if (user == null) {
        User newUser = new UserLocal(null, profile.getUsername(), profile.getEmail(), profile.getIsVerified(),
            profile.getAvatar(), OAuthProvider.GOOGLE);

        this.usersRepository.create(newUser);

        user = newUser;
      }

      Timestamp expiresAt = Timestamp.from(Instant.now().plusSeconds(7 * 24 * 60 * 60 * 10));
      ApiToken apiToken = generateApiToken(user, expiresAt);

      String token = this.generateJWT(apiToken.getToken(), expiresAt);

      ApiTokenDTO apiTokenDTO = new ApiTokenDTO(token, expiresAt);

      return apiTokenDTO;
    }

    throw new BadRequestException("Provider not found");
  }

  private ApiToken generateApiToken(User user, Timestamp expiresAt) {
    ApiToken apiToken = new ApiTokenLocal(null, user.getId(), this.generateRandomToken(),
        expiresAt);

    apiToken.setUser(user);

    this.apiTokenRepository.create(apiToken);

    return apiToken;
  }

  private String generateJWT(String apiTokenId, Date expiresAt) {
    try {
      String token = JWT.create()
          .withClaim("token", apiTokenId)
          .withClaim("expiresAt", expiresAt)
          .withExpiresAt(expiresAt)
          .sign(this.JWTAlgorithm);

      return token;
    } catch (JWTCreationException exception) {
      // Invalid Signing configuration / Couldn't convert Claims.
      exception.printStackTrace();
    }

    return new String();
  }

  private String generateRandomToken() {
    return RandomStringUtils.randomAlphanumeric(128).toUpperCase();
  }
}
