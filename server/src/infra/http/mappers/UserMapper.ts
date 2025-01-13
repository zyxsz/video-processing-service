import type { User } from 'src/app/entities/user';

export class UserMapper {
  static toHttp(user: User) {
    return {
      id: user.id,
      providerId: user.providerId,
      avatar: user.avatar,
      username: user.username,
      email: user.email,
      isEmailVerifiedByProvider: user.isEmailVerifiedByProvider,
      updatedAt: user.updatedAt,
      createdAt: user.createdAt,
    };
  }
}
