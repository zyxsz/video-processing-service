import { Injectable } from '@nestjs/common';
import type { ApiToken } from 'src/app/entities/api-token';
import { ApiTokenRepository } from 'src/app/repositories/api-token-repository';
import { PrismaService } from '../prisma.service';
import { PrismaApiTokenMapper } from '../mappers/prisma-api-token-mapper';

@Injectable()
export class PrismaApiTokenRepository implements ApiTokenRepository {
  constructor(private prisma: PrismaService) {}

  async create(apiToken: ApiToken): Promise<void> {
    await this.prisma.apiToken.create({
      data: PrismaApiTokenMapper.toPrisma(apiToken),
    });
  }

  async findById(id: string): Promise<ApiToken> {
    const prismaApiToken = await this.prisma.apiToken.findUnique({
      where: { id },
    });

    if (!prismaApiToken) return null;

    return PrismaApiTokenMapper.toDomain(prismaApiToken);
  }
}
