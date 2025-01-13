import { getApiFullPath } from "@/lib/utils";
import { AuthenticationApi } from "@/services/api/authentication-api";
import { StoreInitializer } from "@/store";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import type { ReactNode } from "react";

const fetchIsAuthenticated = async () => {
  const data = await AuthenticationApi.getAuthenticatedUser(
    cookies().toString()
  );

  return data;
};

const Layout = async ({ children }: { children: ReactNode }) => {
  const authenticatedUser = await fetchIsAuthenticated();

  if (!authenticatedUser) {
    redirect("/authentication");
  }

  return (
    <StoreInitializer
      initialData={{
        isAuthenticated: !!authenticatedUser,
        authenticatedUser: authenticatedUser,
      }}
    >
      {children}
    </StoreInitializer>
  );
};

export default Layout;
