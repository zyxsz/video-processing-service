import { UserProject } from 'src/app/entities/user-project';
import { UsersOnProjects } from '@prisma/client';

export class PrismaProjectsUsersMapper {
  static toPrisma(userProject: UserProject): UsersOnProjects {
    return {
      userId: userProject.userId,
      projectId: userProject.projectId,
      attachedAt: userProject.attachedAt,
      requestedAt: userProject.requestedAt,
      updatedAt: userProject.updatedAt,
      roleId: userProject.roleId,
      status: userProject.status,
    };
  }
}
