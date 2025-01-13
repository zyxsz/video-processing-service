"use client";
import {
  formatProjectStorageProvider,
  formatProjectStorageStatus,
} from "@/lib/formatter";
import { ProjectStoragesApi } from "@/services/api/project-storages-api";
import { useSuspenseQuery } from "@tanstack/react-query";
import { useParams } from "next/navigation";
import { formatDistance } from "date-fns";
import { ptBR } from "date-fns/locale";
import {
  ArrowLeft,
  ArrowRight,
  BadgeCheck,
  Calendar,
  CheckCheck,
  Dot,
  Trash2,
} from "lucide-react";
import { Button } from "@/components/ui/button";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import Link from "next/link";
import { Storage } from "@/components/Storage";

const Page = () => {
  const { projectId } = useParams();
  const { data } = useSuspenseQuery({
    queryKey: [`project-storages:${projectId}`],
    queryFn: () => ProjectStoragesApi.findProjectStorages(projectId as string),
  });

  return (
    <div>
      <header>
        <h1 className="scroll-m-20 text-3xl font-extrabold tracking-tight lg:text-3xl">
          Armazenamentos
        </h1>
        <p className="text-sm text-muted-foreground">
          Aqui você pode gerenciar todos os armazenamentos associados ao projeto
          selecionado.
        </p>
      </header>

      <div className="mt-8">
        {data.map((storage) => (
          <Storage
            key={storage.id}
            storage={storage}
            projectId={projectId as string}
            showDetailsButton
          />
        ))}
      </div>

      <div className="w-full mt-4">
        <Button className="w-full" variant="outline">
          Adicionar armazenamento
        </Button>
      </div>
    </div>
  );
};

export default Page;
