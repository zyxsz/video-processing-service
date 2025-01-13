import { Sidebar } from "@/components/sidebar";
import type { ReactNode } from "react";

const Layout = ({ children }: { children: ReactNode }) => {
  return (
    <div className="w-full max-w-screen-xl h-full mx-auto p-4 flex gap-8">
      <Sidebar />
      <div className="flex-1">{children}</div>
    </div>
  );
};

export default Layout;
