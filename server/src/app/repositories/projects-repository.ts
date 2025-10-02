import type { Project } from '../entities/project';
import type { UserProject } from '../entities/user-project';

export type ProjectsWithRole = {
  project: Project;
  role: {
    roleId: string;
    status: string;
    attachedAt: Date;
    updatedAt: Date;
    requestedAt: Date;
  };
};

export abstract class ProjectsRepository {
  abstract findByName(name: string): Promise<Project | null>;
  abstract create(project: Project): Promise<void>;
  abstract createUserProject(userProject: UserProject): Promise<void>;
  abstract findAllByUserId(userId: string): Promise<ProjectsWithRole[]>;
}
