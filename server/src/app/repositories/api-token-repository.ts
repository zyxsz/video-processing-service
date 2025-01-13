import type { ApiToken } from '../entities/api-token';

export abstract class ApiTokenRepository {
  abstract create(apiToken: ApiToken): Promise<void>;
  abstract findById(id: string): Promise<ApiToken>;
}
