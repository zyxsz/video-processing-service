import { API_BASE_URL } from "@/services/api";
import { type ClassValue, clsx } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function getApiFullPath(path: string) {
  return `${API_BASE_URL}/`;
}
