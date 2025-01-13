import type {
  AuthenticationOAuthProviderResponse,
  AuthenticationOAuthRedirectResponse,
} from "@/types/api/authentication-api";
import { apiClient } from "../api";
import type { User } from "@/types/user";

export const AuthenticationApi = {
  findOAuth2Providers: () =>
    apiClient
      .get<AuthenticationOAuthProviderResponse[]>(
        "/authentication/oauth2/providers"
      )
      .then((response) => response.data),
  getOAuth2RedirectUrl: (providerId: string) =>
    apiClient
      .get<AuthenticationOAuthRedirectResponse>(
        `authentication/oauth2/${providerId}/redirect`
      )
      .then((response) => response.data)
      .catch(() => null),
  callback: (providerId: string, code: string) =>
    apiClient
      .post<Boolean>(`authentication/oauth2/${providerId}/callback`, { code })
      .then(() => true)
      .catch(() => false),
  getAuthenticatedUser: (cookies: string) =>
    apiClient
      .get<User>("authentication/me", { headers: { Cookie: cookies } })
      .then((response) => response.data)
      .catch(() => null),
};
