"use client";
import { Button } from "@/components/ui/button";
import {
  Captions,
  Clapperboard,
  CloudUpload,
  Database,
  FileAudio,
  Film,
  GalleryThumbnails,
  LayoutDashboard,
  Presentation,
  ServerCog,
  Settings,
  UsersRound,
} from "lucide-react";
import { SidebarProjectHeader, SidebarProjectHeaderFallback } from "./header";
import { Suspense } from "react";
import Link from "next/link";
import { useParams } from "next/navigation";

const Sidebar = () => {
  const { projectId } = useParams();

  return (
    <aside className="w-full max-w-80">
      <header className="mb-4 flex flex-col gap-4">
        <Suspense fallback={<SidebarProjectHeaderFallback />}>
          <SidebarProjectHeader />
        </Suspense>

        <Button
          variant="ghost"
          className="w-full justify-start text-muted-foreground"
          asChild
        >
          <Link href={`/projects/${projectId}`}>
            <LayoutDashboard className="mr-2 h-4 w-4" /> Painel de controle
          </Link>
        </Button>
      </header>
      <div className="w-full flex flex-col gap-4 text-muted-foreground">
        <div className="w-full flex flex-col gap-2">
          <p className="text-sm uppercase font-extrabold tracking-wide text-white">
            Videos
          </p>
          <div className="w-full flex flex-col gap-1 items-start">
            <Button variant="ghost" className="w-full justify-start" asChild>
              <Link href={`/projects/${projectId}/videos`}>
                <Clapperboard className="mr-2 h-4 w-4" /> Meus videos
              </Link>
            </Button>
            <Button variant="ghost" className="w-full justify-start">
              <CloudUpload className="mr-2 h-4 w-4" /> Meus uploads
            </Button>
            <Button variant="ghost" className="w-full justify-start">
              <Presentation className="mr-2 h-4 w-4" /> Minhas predefinições
            </Button>
          </div>
        </div>
        <div className="w-full flex flex-col gap-2">
          <p className="text-sm uppercase font-extrabold tracking-wide text-white">
            Serviços
          </p>
          <div className="w-full flex flex-col gap-1 items-start">
            <Button variant="ghost" className="w-full justify-start">
              <FileAudio className="mr-2 h-4 w-4" /> Prévias
            </Button>
            <Button variant="ghost" className="w-full justify-start">
              <Captions className="mr-2 h-4 w-4" /> Transcrição
            </Button>
            <Button variant="ghost" className="w-full justify-start">
              <Film className="mr-2 h-4 w-4" /> Transcodificação
            </Button>
            <Button variant="ghost" className="w-full justify-start" asChild>
              <Link href={`/projects/${projectId}/storages`}>
                <Database className="mr-2 h-4 w-4" /> Armazenamento
              </Link>
            </Button>
            {/* <Button variant="ghost" className="w-full justify-start">
              <Presentation className="mr-2 h-4 w-4" /> Minhas predefinições
            </Button> */}
          </div>
        </div>
        <div className="w-full flex flex-col gap-2">
          <p className="text-sm uppercase font-extrabold tracking-wide text-white">
            Projeto
          </p>
          <div className="w-full flex flex-col gap-1 items-start">
            {/* <Button variant="ghost" className="w-full justify-start">
          <Settings className="mr-2 h-4 w-4" /> Configurações
        </Button> */}
            <Button variant="ghost" className="w-full justify-start ">
              <UsersRound className="mr-2 h-4 w-4" /> Usuários
            </Button>
            <Button variant="ghost" className="w-full justify-start">
              <Settings className="mr-2 h-4 w-4" /> Configurações
            </Button>
          </div>
        </div>
      </div>
    </aside>
  );
};

export { Sidebar };
