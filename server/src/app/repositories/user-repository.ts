import type { User } from '../entities/user';

export abstract class UserRepository {
  abstract upsert(user: User): Promise<void>;
  abstract findById(id: string): Promise<User>;
}
