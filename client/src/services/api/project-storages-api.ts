import type {
  ProjectStorage,
  ProjectStorageActivity,
} from "@/types/api/project-storages-api";
import { apiClient } from "../api";

export const ProjectStoragesApi = {
  findProjectStorages: (projectId: string) =>
    apiClient
      .get<ProjectStorage[]>(`projects/${projectId}/storages`)
      .then((response) => response.data),
  findStorageDetails: (projectId: string, storageId: string) =>
    apiClient
      .get<ProjectStorage>(`projects/${projectId}/storages/${storageId}`)
      .then((response) => response.data),
  findStorageActivities: (projectId: string, storageId: string) =>
    apiClient
      .get<ProjectStorageActivity[]>(
        `projects/${projectId}/storages/${storageId}/activities`
      )
      .then((response) => response.data),
};
