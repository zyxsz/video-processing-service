import { Injectable } from '@nestjs/common';
import {
  ProjectsRepository,
  ProjectsWithRole,
} from 'src/app/repositories/projects-repository';

type FindProjectsRequest = {
  userId: string;
};

type FindProjectsResponse = ProjectsWithRole[];

@Injectable()
export class FindProjects {
  constructor(private projectRepository: ProjectsRepository) {}

  async execute(request: FindProjectsRequest): Promise<FindProjectsResponse> {
    const projects = await this.projectRepository.findAllByUserId(
      request.userId,
    );

    return projects;
  }
}
