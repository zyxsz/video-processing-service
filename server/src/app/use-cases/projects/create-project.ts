import { Injectable } from '@nestjs/common';
import { OwnerRoleId } from 'src/app/data/roles';
import { Project } from 'src/app/entities/project';
import { UserProject } from 'src/app/entities/user-project';
import { ProjectsRepository } from 'src/app/repositories/projects-repository';

type CreateProjectRequest = {
  name: string;
  userId: string;
};
type CreateProjectResponse = { project: Project };

@Injectable()
export class CreateProject {
  constructor(private projectRepository: ProjectsRepository) {}

  async execute(request: CreateProjectRequest): Promise<CreateProjectResponse> {
    const alreadyExistsProject = await this.projectRepository.findByName(
      request.name,
    );

    if (alreadyExistsProject) throw new Error('Invalid project name');

    const project = new Project(null, { name: request.name });

    await this.projectRepository.create(project);

    const userProject = new UserProject(request.userId, project.id, {
      roleId: OwnerRoleId,
      status: 'ACCEPTED',
    });

    await this.projectRepository.createUserProject(userProject);

    return { project };
  }
}
