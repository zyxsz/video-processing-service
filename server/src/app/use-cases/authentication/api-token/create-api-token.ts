import { Injectable } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { ApiToken } from 'src/app/entities/api-token';
import { ApiTokenRepository } from 'src/app/repositories/api-token-repository';

type CreateApiTokenRequest = {
  userId: string;
  expiresAt: Date;
  ipAddress: string | null;
  location: string | null;
};

type CreateApiTokenResponse = {
  apiToken: ApiToken;
  jwtToken: string;
};

@Injectable()
export class CreateApiToken {
  constructor(
    private apiTokenRepository: ApiTokenRepository,
    private jwtService: JwtService,
  ) {}

  async execute(
    request: CreateApiTokenRequest,
  ): Promise<CreateApiTokenResponse> {
    const apiToken = new ApiToken(null, {
      enabled: true,
      expiresAt: request.expiresAt,
      ipAddress: request.ipAddress,
      location: request.location,
      userId: request.userId,
    });

    console.log(this.apiTokenRepository);

    await this.apiTokenRepository.create(apiToken);

    const token = await this.jwtService.signAsync(
      { id: apiToken.id },
      { expiresIn: '30d' },
    );

    return { apiToken, jwtToken: token };
  }
}
