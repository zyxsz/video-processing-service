"use client";
import { useSuspenseQuery } from "@tanstack/react-query";
import { Avatar, AvatarFallback } from "../ui/avatar";
import { useParams } from "next/navigation";
import { ProjectsApi } from "@/services/api/projects-api";
import { Skeleton } from "../ui/skeleton";
import { formatDistanceToNow } from "date-fns";
import { ptBR } from "date-fns/locale";

const SidebarProjectHeader = () => {
  const { projectId } = useParams();

  const { data } = useSuspenseQuery({
    queryKey: [`projects:${projectId}`],
    queryFn: () => ProjectsApi.getProject(projectId as string),
  });

  return (
    <div className=" flex items-center space-x-4 rounded-md border p-4">
      <Avatar>
        <AvatarFallback>MP</AvatarFallback>
      </Avatar>
      <div className="flex-1 space-y-1">
        <p className="text-primary text-base font-bold leading-none">
          {data.name}
        </p>

        <p className="text-sm text-muted-foreground">
          Criado há{" "}
          <strong>
            {formatDistanceToNow(data.createdAt, { locale: ptBR })}
          </strong>
        </p>
      </div>
    </div>
  );
};

const SidebarProjectHeaderFallback = () => {
  return (
    <div className=" flex items-center space-x-4 rounded-md border p-4">
      <Skeleton style={{ width: 40, height: 40 }} className="rounded-full" />
      <div className="flex-1 space-y-1">
        <Skeleton style={{ width: 180, height: 16 }} />
        <Skeleton style={{ width: 110, height: 20 }} />
      </div>
      {/* <Switch /> */}
    </div>
  );
};

export { SidebarProjectHeader, SidebarProjectHeaderFallback };
