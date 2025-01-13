import { Body, Controller, Get, Param, Post, Res } from '@nestjs/common';
import { ProviderRepository } from 'src/app/repositories/provider-repository';
import { GenerateOAuth2RedirectUrl } from 'src/app/use-cases/authentication/oauth2/generate-oauth2-redirect-url';
import { OAuth2CallbackBody } from '../dto/oauth2-callback-body';
import { GetUserFromOAuth2 } from 'src/app/use-cases/authentication/oauth2/get-user-from-oauth2';
import { CreateApiToken } from 'src/app/use-cases/authentication/api-token/create-api-token';
import type { Response } from 'express';
import { addDays } from 'date-fns';
import { UserMapper } from '../mappers/UserMapper';
import { Auth } from '../decorators/auth.decorator';
import type { User } from 'src/app/entities/user';
import { AuthenticatedUser } from '../decorators/authenticated-user.decorator';

@Controller('authentication')
export class AuthenticationController {
  constructor(
    private providerRepository: ProviderRepository,
    private generateOAuth2RedirectUrl: GenerateOAuth2RedirectUrl,
    private getUserFromOAuth2: GetUserFromOAuth2,
    private createApiToken: CreateApiToken,
  ) {}

  @Get('me')
  @Auth()
  async getAuthenticatedUser(@AuthenticatedUser() user: User) {
    return user.toJson();
  }

  @Get('oauth2/providers')
  async OAuth2GetProviders() {
    const providers = await this.providerRepository.findAllEnabled();

    return providers.map((provider) => ({
      id: provider.id,
      identifier: provider.identifier,
      name: provider.name,
      enabled: provider.enabled,
    }));
  }

  @Get('oauth2/:providerId/redirect')
  async OAuth2Redirect(@Param('providerId') providerId: string) {
    const provider = await this.providerRepository.findById(providerId);

    if (!provider.enabled) throw new Error('Provider not enabled');

    const response = await this.generateOAuth2RedirectUrl.execute({
      identifier: provider.identifier,
    });

    return response;
  }

  @Post('oauth2/:providerId/callback')
  async OAuth2Callback(
    @Param('providerId') providerId: string,
    @Body() body: OAuth2CallbackBody,
    @Res({ passthrough: true }) response: Response,
  ) {
    const provider = await this.providerRepository.findById(providerId);

    if (!provider.enabled) throw new Error('Provider not enabled');

    const expiresAt = addDays(new Date(), 30);

    const { user } = await this.getUserFromOAuth2.execute({
      providerId: provider.id,
      code: body.code,
      identifier: provider.identifier,
    });

    const { jwtToken } = await this.createApiToken.execute({
      expiresAt,
      userId: user.id,
      ipAddress: null,
      location: null,
    });

    response.cookie('authentication', jwtToken, {
      httpOnly: true,
      secure: true,
      maxAge: expiresAt.getTime(),
    });

    return UserMapper.toHttp(user);
  }
}
