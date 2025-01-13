type AuthenticationOAuthProvider = "GOOGLE" | "GITHUB";

export type AuthenticationOAuthProviderResponse = {
  name: string;
  id: AuthenticationOAuthProvider;
};

export type AuthenticationOAuthRedirectResponse = {
  redirectUrl: string;
};

export type AuthenticationProvider = "GOOGLE" | "GITHUB";
