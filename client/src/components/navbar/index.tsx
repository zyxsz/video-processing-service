import { Suspense } from "react";
import { NavbarUserAvatar } from "./user-avatar";
import { NavbarUserProjects } from "./user-projects";
import { Skeleton } from "../ui/skeleton";
import Link from "next/link";

const Navbar = () => {
  return (
    <nav className="w-full h-full min-h-16 border-b flex items-center justify-center">
      <div className="w-full max-w-screen-xl h-full mx-auto p-4">
        <div className="h-full flex items-center justify-between">
          <div>
            <Link href="/">
              <p>VPS</p>
            </Link>
          </div>
          <div className="flex items-center justify-end gap-6">
            <Suspense fallback={<Skeleton className="w-[260px] h-[40px]" />}>
              <NavbarUserProjects />
            </Suspense>
            <NavbarUserAvatar />
          </div>
        </div>
      </div>
    </nav>
  );
};

export { Navbar };
