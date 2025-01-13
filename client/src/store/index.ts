"use client";

import type { User } from "@/types/user";
import type { ReactNode } from "react";
import { useAuth } from "./auth-store";

type StoreInitializerProps = {
  children: ReactNode;
  initialData?: {
    authenticatedUser?: User | null;
    isAuthenticated?: boolean;
  };
};

const StoreInitializer = ({ children, initialData }: StoreInitializerProps) => {
  if (initialData) {
    useAuth.setState({
      isAuthenticated: initialData.isAuthenticated,
      authenticatedUser: initialData.authenticatedUser,
    });
  }

  return children;
};

export { StoreInitializer };
