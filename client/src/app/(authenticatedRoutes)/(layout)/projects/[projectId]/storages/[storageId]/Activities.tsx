"use client";
import { Activity } from "@/components/Activity";
import { ProjectStoragesApi } from "@/services/api/project-storages-api";
import { useSuspenseQuery } from "@tanstack/react-query";

type Props = {
  projectId: string;
  storageId: string;
  // activities: ProjectStorageActivity[];
};

const Activities = ({ projectId, storageId }: Props) => {
  const { data } = useSuspenseQuery({
    queryKey: [`project:${projectId}:storage:${storageId}:activities`],
    queryFn: () =>
      ProjectStoragesApi.findStorageActivities(projectId, storageId),
  });

  return (
    <section className="border rounded-lg">
      <header className="p-4 border-b">
        <h1 className="flex items-center scroll-m-20 text-2xl font-extrabold tracking-tight">
          Atividades
        </h1>
        <p className="text-sm text-muted-foreground">
          Aqui você pode ver o historico de ações relacionadas ao armazenamento
          selecionado
        </p>
      </header>
      <div className="p-4">
        <div className="flex flex-col gap-2">
          {data.map((activity) => (
            <Activity activity={activity} key={activity.id} />
          ))}
        </div>
      </div>
    </section>
  );
};

export { Activities };
