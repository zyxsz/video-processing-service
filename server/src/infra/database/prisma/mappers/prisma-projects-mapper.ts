import { Project as RawProject } from '@prisma/client';
import { Project } from 'src/app/entities/project';

export class PrismaProjectsMapper {
  static toPrisma(project: Project): RawProject {
    return {
      id: project.id,
      name: project.name,

      createdAt: project.createdAt,
      updatedAt: project.updatedAt,
    };
  }

  static toDomain(raw: RawProject): Project {
    const project = new Project(raw.id, {
      name: raw.name,
      createdAt: raw.createdAt,
      updatedAt: raw.updatedAt,
    });

    return project;
  }
}
