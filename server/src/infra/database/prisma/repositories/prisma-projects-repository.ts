import { Injectable } from '@nestjs/common';
import {
  ProjectsRepository,
  type ProjectsWithRole,
} from 'src/app/repositories/projects-repository';
import { PrismaService } from '../prisma.service';
import { Project } from 'src/app/entities/project';
import { PrismaProjectsMapper } from '../mappers/prisma-projects-mapper';
import { UserProject } from 'src/app/entities/user-project';
import { PrismaProjectsUsersMapper } from '../mappers/prisma-projects-users-mapper';

@Injectable()
export class PrismaProjectsRepository implements ProjectsRepository {
  constructor(private prisma: PrismaService) {}

  async create(project: Project): Promise<void> {
    await this.prisma.project.create({
      data: PrismaProjectsMapper.toPrisma(project),
    });
  }

  async createUserProject(userProject: UserProject): Promise<void> {
    await this.prisma.usersOnProjects.create({
      data: PrismaProjectsUsersMapper.toPrisma(userProject),
    });
  }

  async findByName(name: string): Promise<Project | null> {
    const rawProject = await this.prisma.project.findFirst({
      where: {
        name: {
          equals: name,
          mode: 'insensitive',
        },
      },
    });

    if (!rawProject) return null;

    return PrismaProjectsMapper.toDomain(rawProject);
  }

  async findAllByUserId(userId: string): Promise<ProjectsWithRole[]> {
    const rawProjects = await this.prisma.project.findMany({
      where: { users: { some: { userId } } },
      select: {
        id: true,
        name: true,
        updatedAt: true,
        createdAt: true,
        users: {
          where: { userId },
          select: {
            roleId: true,
            status: true,
            attachedAt: true,
            updatedAt: true,
            requestedAt: true,
          },
        },
      },
    });

    return rawProjects.map((rawProject) => ({
      project: PrismaProjectsMapper.toDomain(rawProject),
      role: rawProject.users?.[0],
    }));
  }
}
