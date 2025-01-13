import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';

type GenerateOAuth2RedirectUrlRequest = {
  identifier: string;
};

type GenerateOAuth2RedirectUrlResponse = {
  redirectUrl: string;
};

@Injectable()
export class GenerateOAuth2RedirectUrl {
  constructor(private configService: ConfigService) {}

  async execute(
    request: GenerateOAuth2RedirectUrlRequest,
  ): Promise<GenerateOAuth2RedirectUrlResponse> {
    switch (request.identifier) {
      case 'GOOGLE':
        const redirectUrl = `${this.configService.get<string>('OAUTH2_GOOGLE_AUTH_ENDPOINT')}?client_id=${this.configService.get('OAUTH2_GOOGLE_CLIENT_ID')}&redirect_uri=${this.configService.get('OAUTH2_GOOGLE_REDIRECT_URL')}&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile+openid&include_granted_scopes=true&prompt=consent`;

        return { redirectUrl };
      default:
        throw new Error('Invalid provider');
    }
  }
}
