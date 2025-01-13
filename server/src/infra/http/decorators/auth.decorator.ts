import { applyDecorators, UseGuards } from '@nestjs/common';
import { AuthGuard } from '../guards/auth.guard';

export function Auth() {
  //...permissions: string[]
  // if (!permissions || !permissions.length)
  //   return applyDecorators(UseGuards(JwtAuthGuard));

  return applyDecorators(
    UseGuards(AuthGuard),
    // Permissions(...permissions),
  );
  // SetMetadata('roles', roles),
  // UseGuards(AuthGuard, RolesGuard),
  // ApiBearerAuth(),
  // ApiUnauthorizedResponse({ description: 'Unauthorized' }),
}
