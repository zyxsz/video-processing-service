import { Injectable } from '@nestjs/common';
import { User } from 'src/app/entities/user';
import { UserRepository } from 'src/app/repositories/user-repository';
import { PrismaService } from '../prisma.service';
import { PrismaUserMapper } from '../mappers/prisma-user-mapper';

@Injectable()
export class PrismaUserRepository implements UserRepository {
  constructor(private prisma: PrismaService) {}

  async upsert(user: User): Promise<void> {
    await this.prisma.user.upsert({
      where: { email: user.email },
      create: {
        id: user.id,
        email: user.email,
        username: user.username,
        avatar: user.avatar,
        isEmailVerifiedByProvider: user.isEmailVerifiedByProvider,
        providerId: user.providerId,
        updatedAt: user.updatedAt,
        createdAt: user.createdAt,
      },
      update: {
        id: user.id,
        email: user.email,
        username: user.username,
        avatar: user.avatar,
        isEmailVerifiedByProvider: user.isEmailVerifiedByProvider,
        providerId: user.providerId,
        updatedAt: user.updatedAt,
        createdAt: user.createdAt,
      },
    });
  }

  async findById(id: string): Promise<User> {
    const prismaUser = await this.prisma.user.findUnique({ where: { id } });

    return PrismaUserMapper.toDomain(prismaUser);
  }
}
