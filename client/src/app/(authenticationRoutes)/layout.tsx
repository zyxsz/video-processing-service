import { getApiFullPath } from "@/lib/utils";
import { AuthenticationApi } from "@/services/api/authentication-api";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import type { ReactNode } from "react";

const fetchIsAuthenticated = async () => {
  const data = await AuthenticationApi.getAuthenticatedUser(
    cookies().toString()
  );

  return data || false;
};

const Layout = async ({ children }: { children: ReactNode }) => {
  const isAuthenticated = await fetchIsAuthenticated();

  if (isAuthenticated) {
    redirect("/");
  }

  return children;
};

export default Layout;
