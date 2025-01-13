"use client";

import { useCookies } from "next-client-cookies";
import { Check, ChevronsUpDown } from "lucide-react";
import { Button } from "../ui/button";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "../ui/command";
import { cn } from "@/lib/utils";
import { useState } from "react";
import { useSuspenseQuery } from "@tanstack/react-query";
import { ProjectsApi } from "@/services/api/projects-api";
import { useParams, useRouter } from "next/navigation";
import Link from "next/link";

const frameworks = [
  {
    value: "next.js",
    label: "Next.js",
  },
  {
    value: "sveltekit",
    label: "SvelteKit",
  },
  {
    value: "nuxt.js",
    label: "Nuxt.js",
  },
  {
    value: "remix",
    label: "Remix",
  },
  {
    value: "astro",
    label: "Astro",
  },
];

const NavbarUserProjects = () => {
  const router = useRouter();
  const { projectId } = useParams();

  const { data, isLoading } = useSuspenseQuery({
    queryKey: ["projects:list"],
    queryFn: ProjectsApi.getProjects,
  });

  const [open, setOpen] = useState(false);

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button
          variant="outline"
          role="combobox"
          aria-expanded={open}
          className="w-[260px] justify-between"
        >
          {projectId
            ? data.find((project) => project.id === projectId)?.name
            : "Selecione um projeto..."}
          <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
        </Button>
      </PopoverTrigger>
      <PopoverContent className="w-[260px] p-0">
        <Command>
          <CommandInput placeholder="Procure projetos..." />
          <CommandList>
            <CommandEmpty>Nenhum projeto encontrado.</CommandEmpty>
            <CommandGroup>
              {data.map((project) => (
                <CommandItem
                  className="cursor-pointer"
                  key={project.id}
                  value={project.id}
                  onSelect={() => setOpen(false)}
                  asChild
                >
                  <Link href={`/projects/${project.id}`}>
                    <Check
                      className={cn(
                        "mr-2 h-4 w-4",
                        projectId === project.id ? "opacity-100" : "opacity-0"
                      )}
                    />
                    {project.name}
                  </Link>
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
};

export { NavbarUserProjects };
