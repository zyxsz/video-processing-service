import { HttpService } from '@nestjs/axios';
import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { User } from 'src/app/entities/user';
import { UserRepository } from 'src/app/repositories/user-repository';

type GetUserFromOAuth2Request = {
  providerId: string;
  identifier: string;
  code: string;
};

type TokenResponse = {
  access_token: string;
  expires_in: number;
  scope: string;
  token_type: string;
  id_token: string;
};

type ProfileResponse = {
  id: string;
  email: string;
  verified_email: boolean;
  name: string;
  given_name: string;
  picture: string;
};

type GetUserFromOAuth2Response = {
  user: User;
};

@Injectable()
export class GetUserFromOAuth2 {
  constructor(
    private httpService: HttpService,
    private configService: ConfigService,
    private userRepository: UserRepository,
  ) {}

  async execute(
    request: GetUserFromOAuth2Request,
  ): Promise<GetUserFromOAuth2Response> {
    switch (request.identifier) {
      case 'GOOGLE':
        const tokenRequestUrl = `${this.configService.get('OAUTH2_GOOGLE_TOKEN_ENDPOINT')}?code=${request.code}&client_id=${this.configService.get('OAUTH2_GOOGLE_CLIENT_ID')}&client_secret=${this.configService.get('OAUTH2_GOOGLE_CLIENT_SECRET')}&redirect_uri=${this.configService.get('OAUTH2_GOOGLE_REDIRECT_URL')}&grant_type=authorization_code`;

        const tokenResponse = await this.httpService.axiosRef
          .post<TokenResponse>(tokenRequestUrl)
          .then((response) => response.data);

        const profileRequestUrl = `${this.configService.get('OAUTH2_GOOGLE_PROFILE_ENDPOINT')}`;

        const profileResponse = await this.httpService.axiosRef
          .get<ProfileResponse>(profileRequestUrl, {
            headers: {
              Authorization: `${tokenResponse.token_type} ${tokenResponse.access_token}`,
            },
          })
          .then((response) => response.data);

        const user = new User(null, {
          username: profileResponse.given_name,
          email: profileResponse.email,
          avatar: profileResponse.picture,
          isEmailVerifiedByProvider: profileResponse.verified_email,
          providerId: request.providerId,
        });

        await this.userRepository.upsert(user);

        return { user };
      default:
        throw new Error('Invalid provider');
    }
  }
}
