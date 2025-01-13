"use client";
import { Storage } from "@/components/Storage";
import { Button } from "@/components/ui/button";
import { formatProjectStorageActivityOperationType } from "@/lib/formatter";
import { ProjectStoragesApi } from "@/services/api/project-storages-api";
import { useSuspenseQuery } from "@tanstack/react-query";
import { format } from "date-fns";
import { ArrowLeft, Calendar } from "lucide-react";
import Link from "next/link";
import { useParams } from "next/navigation";

import { Activities } from "./Activities";
import { ActivitiesSkeleton } from "./ActivitiesSkeleton";
import { Suspense } from "react";

const Page = () => {
  const { projectId, storageId } = useParams();

  const { data } = useSuspenseQuery({
    queryKey: [`project:${projectId}:storage:${storageId}`],
    queryFn: () =>
      ProjectStoragesApi.findStorageDetails(
        projectId as string,
        storageId as string
      ),
  });

  return (
    <main className="flex flex-col gap-4">
      <section>
        <header>
          <Button
            variant="link"
            className="pl-0 text-muted-foreground hover:text-primary"
            asChild
          >
            <Link href={`/projects/${projectId}/storages`}>
              <ArrowLeft className="mr-2 h-4 w-4" /> Voltar
            </Link>
          </Button>
          <h1 className="flex items-center scroll-m-20 text-3xl font-extrabold tracking-tight lg:text-3xl">
            Armazenamento{" "}
            <strong className="font-bold ml-4 text-xl">
              {data.bucketName}
            </strong>
          </h1>
          <p className="text-sm text-muted-foreground">
            Aqui você pode ver todos os detalhes do amarzenamento selecionado.
          </p>
        </header>

        <div className="mt-8">
          <Storage storage={data} projectId={projectId as string} />
        </div>
      </section>
      <Suspense fallback={<ActivitiesSkeleton />}>
        <Activities
          projectId={projectId as string}
          storageId={storageId as string}
        />
      </Suspense>
    </main>
  );
};

export default Page;
