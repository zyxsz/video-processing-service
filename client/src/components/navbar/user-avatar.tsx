"use client";

import { useAuth } from "@/store/auth-store";
import { Avatar, AvatarFallback, AvatarImage } from "../ui/avatar";

const NavbarUserAvatar = () => {
  const user = useAuth((state) => state.authenticatedUser);

  return (
    <Avatar>
      <AvatarImage src={user?.avatar} alt={user?.username} />
      <AvatarFallback>AV</AvatarFallback>
    </Avatar>
  );
};

export { NavbarUserAvatar };
