import { Navbar } from "@/components/navbar";
import { Fragment, type ReactNode } from "react";

const Layout = ({ children }: { children: ReactNode }) => {
  return (
    <Fragment>
      <Navbar />
      {children}
    </Fragment>
  );
};

export default Layout;
