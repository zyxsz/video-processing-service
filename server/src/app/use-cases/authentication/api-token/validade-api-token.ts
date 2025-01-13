import { Injectable } from '@nestjs/common';
import { isBefore } from 'date-fns';
import { ApiToken } from 'src/app/entities/api-token';
import { User } from 'src/app/entities/user';
import { ApiTokenRepository } from 'src/app/repositories/api-token-repository';
import { UserRepository } from 'src/app/repositories/user-repository';

type ValidateApiTokenRequest = {
  apiTokenId: string;
};

type ValidateApiTokenResponse = {
  isValid: boolean;
  user: User;
  apiToken: ApiToken;
};

@Injectable()
export class ValidateApiToken {
  constructor(
    private apiTokenRepository: ApiTokenRepository,
    private userRepository: UserRepository,
  ) {}

  async execute(
    request: ValidateApiTokenRequest,
  ): Promise<ValidateApiTokenResponse> {
    const apiToken = await this.apiTokenRepository.findById(request.apiTokenId);

    if (!apiToken) throw new Error('Api token not found');

    if (isBefore(apiToken.expiresAt, new Date())) {
      console.log('BEFORE');

      throw new Error('Expired api token');
    }

    const user = await this.userRepository.findById(apiToken.userId);

    return { isValid: true, user, apiToken };
  }
}
