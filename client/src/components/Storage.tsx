import {
  formatProjectStorageProvider,
  formatProjectStorageStatus,
} from "@/lib/formatter";
import type { ProjectStorage } from "@/types/api/project-storages-api";
import { ArrowRight, BadgeCheck, Calendar, Dot, Trash2 } from "lucide-react";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "./ui/tooltip";
import { Button } from "./ui/button";
import Link from "next/link";
import { formatDistance } from "date-fns";
import { ptBR } from "date-fns/locale";

type Props = {
  projectId: string;
  storage: ProjectStorage;
  showDetailsButton?: boolean;
};

const Storage = ({ projectId, storage, showDetailsButton }: Props) => {
  return (
    <div className="relative border rounded-lg bg-background transition-all">
      <header className="p-4 flex items-center justify-between">
        <div>
          <div className="flex gap-1 items-center">
            <p className="text-sm text-muted-foreground select-none">
              {formatProjectStorageProvider()}
            </p>
            <Dot className="text-muted-foreground" />
            <p className="text-sm text-muted-foreground">{storage.region}</p>
          </div>
          <h4 className="scroll-m-20 text-xl font-semibold tracking-tight">
            {storage.bucketName}
          </h4>
        </div>
        <div className="flex items-center gap-2">
          <TooltipProvider delayDuration={100} disableHoverableContent>
            <Tooltip>
              <TooltipTrigger asChild>
                <Button variant="secondary" size="icon">
                  <Trash2 className="h-4 w-4" />
                </Button>
              </TooltipTrigger>
              <TooltipContent className="pointer-events-none">
                <p>Excluir</p>
              </TooltipContent>
            </Tooltip>
          </TooltipProvider>
          {showDetailsButton && (
            <Button variant="secondary" asChild>
              <Link href={`/projects/${projectId}/storages/${storage.id}`}>
                Ver detalhes <ArrowRight className="ml-2 h-4 w-4" />
              </Link>
            </Button>
          )}
        </div>
      </header>
      <div className="p-4 pt-0 border-t select-none">
        <div className="mt-4 flex gap-4 items-center">
          <span className="text-muted-foreground flex items-center">
            <BadgeCheck className="mr-2 w-5 h-5" />
            <p className="text-sm">
              {formatProjectStorageStatus(storage.status)}
            </p>
          </span>

          <span className="text-muted-foreground flex items-center">
            <Calendar className="mr-2 w-5 h-5" />
            <p className="text-sm">
              Criado há{" "}
              {formatDistance(new Date(), storage.createdAt, {
                locale: ptBR,
              })}
            </p>
          </span>
        </div>
      </div>
    </div>
  );
};

export { Storage };
