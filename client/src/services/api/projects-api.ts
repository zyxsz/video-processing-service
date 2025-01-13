import type { Project } from "@/types/api/projects-api";
import { apiClient } from "../api";

const ProjectsApi = {
  getProjects: () =>
    apiClient.get<Project[]>("projects").then((response) => response.data),
  getProject: (projectId: string) =>
    apiClient
      .get<Project>(`projects/${projectId}`)
      .then((response) => response.data),
};

export { ProjectsApi };
