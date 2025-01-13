import type { AuthenticationProvider } from "./api/authentication-api";

export type User = {
  id: string;
  username: string;
  email: string;
  isEmailVerifiedByProvider: boolean;
  avatar: string;
  provider: AuthenticationProvider;
  updatedAt: number;
  createdAt: number;
};
