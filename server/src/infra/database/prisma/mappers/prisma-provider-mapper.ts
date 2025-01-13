import type { Provider as RawProvider } from '@prisma/client';
import { Provider } from 'src/app/entities/provider';

export class PrismaProviderMapper {
  static toDomain(raw: RawProvider): Provider {
    const provider = new Provider(raw.id, {
      enabled: raw.enabled,
      identifier: raw.identifier,
      name: raw.name,
      updatedAt: raw.updatedAt,
      createdAt: raw.createdAt,
    });

    return provider;
  }
}
