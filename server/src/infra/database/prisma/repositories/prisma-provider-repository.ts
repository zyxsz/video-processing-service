import { Injectable, OnModuleInit } from '@nestjs/common';
import type { Provider } from 'src/app/entities/provider';
import { ProviderRepository } from 'src/app/repositories/provider-repository';
import { PrismaService } from '../prisma.service';
import { DefaultProviders } from 'src/app/data/default-providers';
import { generateUUID } from 'src/helpers/uuid';
import { PrismaProviderMapper } from '../mappers/prisma-provider-mapper';

@Injectable()
export class PrismaProviderRepository
  implements ProviderRepository, OnModuleInit
{
  constructor(private prismaService: PrismaService) {}

  async onModuleInit() {
    if ((await this.prismaService.provider.count()) > 0) return;

    await this.prismaService.provider.createMany({
      data: DefaultProviders.map((provider) => ({
        id: generateUUID(),
        identifier: provider.identifier,
        name: provider.name,
        enabled: provider.enabled,
        updatedAt: new Date(),
        createdAt: new Date(),
      })),
    });
  }

  async create(provider: Provider): Promise<void> {
    await this.prismaService.provider.create({
      data: {
        id: provider.id,
        identifier: provider.identifier,
        name: provider.name,
        enabled: provider.enabled,
        updatedAt: provider.updatedAt,
        createdAt: provider.createdAt,
      },
    });
  }

  async findAllEnabled(): Promise<Provider[]> {
    const raw = await this.prismaService.provider.findMany({
      where: { enabled: true },
    });

    return raw.map((rawProvider) => PrismaProviderMapper.toDomain(rawProvider));
  }

  async findById(id: string): Promise<Provider> {
    const provider = await this.prismaService.provider.findUniqueOrThrow({
      where: { id },
    });

    return PrismaProviderMapper.toDomain(provider);
  }
}
