import type { User } from "@/types/user";
import { create } from "zustand";

type AuthStore = {
  isAuthenticated: boolean;
  authenticatedUser: User | null;
};

const useAuth = create<AuthStore>(() => ({
  isAuthenticated: false,
  authenticatedUser: null,
}));

export { useAuth };
