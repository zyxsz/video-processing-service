import { ApiToken } from 'src/app/entities/api-token';
import { ApiToken as RawApiToken } from '@prisma/client';

export class PrismaApiTokenMapper {
  static toPrisma(apiToken: ApiToken): RawApiToken {
    return {
      id: apiToken.id,
      createdAt: apiToken.createdAt,
      enabled: apiToken.enabled,
      expiresAt: apiToken.expiresAt,
      ipAddress: apiToken.ipAddress,
      location: apiToken.ipAddress,
      updatedAt: apiToken.updatedAt,
      userId: apiToken.userId,
    };
  }

  static toDomain(raw: RawApiToken): ApiToken {
    const apiToken = new ApiToken(raw.id, {
      createdAt: raw.createdAt,
      enabled: raw.enabled,
      expiresAt: raw.expiresAt,
      ipAddress: raw.ipAddress,
      location: raw.ipAddress,
      updatedAt: raw.updatedAt,
      userId: raw.userId,
    });

    return apiToken;
  }
}
