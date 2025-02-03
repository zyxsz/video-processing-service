import { Body, Controller, Get, Post } from '@nestjs/common';
import { CreateProjectDto } from '../dto/create-project-dto';
import { CreateProject } from 'src/app/use-cases/projects/create-project';
import { Auth } from '../decorators/auth.decorator';
import { User } from 'src/app/entities/user';
import { AuthenticatedUser } from '../decorators/authenticated-user.decorator';
import { FindProjects } from 'src/app/use-cases/projects/find-projects';

@Controller('projects')
export class ProjectsController {
  constructor(
    private createProject: CreateProject,
    private findProjects: FindProjects,
  ) {}

  @Post()
  @Auth()
  async create(
    @Body() body: CreateProjectDto,
    @AuthenticatedUser() user: User,
  ) {
    const response = await this.createProject.execute({
      name: body.name,
      userId: user.id,
    });

    return response.project.toJson();
  }

  @Get()
  @Auth()
  async findProjectsRoute(@AuthenticatedUser() user: User) {
    const response = await this.findProjects.execute({ userId: user.id });

    return response.map((p) => ({ project: p.project.toJson(), role: p.role }));
  }
}
