import { Module } from '@nestjs/common';

import { PrismaService } from './prisma/prisma.service';
import { ProviderRepository } from 'src/app/repositories/provider-repository';
import { PrismaProviderRepository } from './prisma/repositories/prisma-provider-repository';
import { UserRepository } from 'src/app/repositories/user-repository';
import { PrismaUserRepository } from './prisma/repositories/prisma-user-repository';
import { ApiTokenRepository } from 'src/app/repositories/api-token-repository';
import { PrismaApiTokenRepository } from './prisma/repositories/prisma-api-token-repository';

@Module({
  providers: [
    PrismaService,
    {
      provide: ProviderRepository,
      useClass: PrismaProviderRepository,
    },
    { provide: UserRepository, useClass: PrismaUserRepository },
    { provide: ApiTokenRepository, useClass: PrismaApiTokenRepository },
  ],
  exports: [ProviderRepository, UserRepository, ApiTokenRepository],
})
export class DatabaseModule {}
