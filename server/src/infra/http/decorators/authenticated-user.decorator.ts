import { createParamDecorator, ExecutionContext } from '@nestjs/common';
import { User as EntityUser } from 'src/app/entities/user';

export const AuthenticatedUser = createParamDecorator(
  (data: any, ctx: ExecutionContext): EntityUser => {
    const request = ctx.switchToHttp().getRequest();
    return request.user;
  },
);
