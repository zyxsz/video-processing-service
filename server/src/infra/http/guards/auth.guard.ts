import {
  Injectable,
  CanActivate,
  ExecutionContext,
  HttpStatus,
  HttpException,
} from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import type { Request } from 'express';
import type { ApiToken } from 'src/app/entities/api-token';
import type { User } from 'src/app/entities/user';
import { ValidateApiToken } from 'src/app/use-cases/authentication/api-token/validade-api-token';

type RequestWithData = Request & {
  user?: User;
  apiToken?: ApiToken;
};

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(
    private jwtService: JwtService,
    private validateApiToken: ValidateApiToken,
  ) {}

  async canActivate(context: ExecutionContext): Promise<boolean> {
    const request = context.switchToHttp().getRequest<RequestWithData>();

    const { authentication } = request.cookies;

    if (!authentication)
      throw new HttpException(
        'Invalid authentication cookie',
        HttpStatus.UNAUTHORIZED,
      );

    const { id } = this.jwtService.decode(authentication);

    const response = await this.validateApiToken.execute({ apiTokenId: id });

    if (!response.isValid)
      throw new HttpException('Unauthorized', HttpStatus.UNAUTHORIZED);

    request.user = response.user;
    request.apiToken = response.apiToken;

    return true;
  }
}
