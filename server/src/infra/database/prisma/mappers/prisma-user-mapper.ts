import type { User as RawUser } from '@prisma/client';
import { User } from 'src/app/entities/user';

export class PrismaUserMapper {
  static toDomain(raw: RawUser): User {
    const user = new User(raw.id, {
      avatar: raw.avatar,
      email: raw.email,
      isEmailVerifiedByProvider: raw.isEmailVerifiedByProvider,
      providerId: raw.providerId,
      username: raw.providerId,
      createdAt: raw.createdAt,
      updatedAt: raw.updatedAt,
    });

    return user;
  }
}
