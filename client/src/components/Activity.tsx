"use client";
import { formatProjectStorageActivityOperationType } from "@/lib/formatter";
import type { ProjectStorageActivity } from "@/types/api/project-storages-api";
import { format } from "date-fns";
import { Calendar } from "lucide-react";
import { JsonView, darkStyles } from "react-json-view-lite";
import "react-json-view-lite/dist/index.css";

type Props = {
  activity: ProjectStorageActivity;
};

const Activity = ({ activity }: Props) => {
  return (
    <div className="border rounded">
      <header className="p-2 border-b flex items-center justify-between">
        <p className="text-xs text-muted-foreground">
          {formatProjectStorageActivityOperationType(activity.operationType)}
        </p>
        <p className="text-xs text-muted-foreground">{activity.id}</p>
      </header>
      <div className="p-2 border-b">
        <JsonView
          data={JSON.parse(activity.operationData)}
          shouldExpandNode={(level) => (level === 0 ? false : true)}
          style={{
            ...darkStyles,
            container: "bg-none text-sm",
            label: "font-normal mr-2",
          }}
        />
      </div>
      <footer className="p-2 ">
        <span className="flex gap-2 items-center text-muted-foreground">
          <Calendar className="mr-1 w-3 h-3" />
          <p className="text-xs">
            Operação executada em{" "}
            {format(activity.executedAt, "dd/MM/yyyy HH:mm")}
          </p>
        </span>
      </footer>
    </div>
  );
};

export { Activity };
