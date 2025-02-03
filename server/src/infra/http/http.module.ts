import { Module } from '@nestjs/common';
import { DatabaseModule } from '../database/database.module';
import { AuthenticationController } from './controllers/authentication.controller';
import { GenerateOAuth2RedirectUrl } from 'src/app/use-cases/authentication/oauth2/generate-oauth2-redirect-url';
import { GetUserFromOAuth2 } from 'src/app/use-cases/authentication/oauth2/get-user-from-oauth2';
import { HttpModule as HttpModuleAxios } from '@nestjs/axios';
import { CreateApiToken } from 'src/app/use-cases/authentication/api-token/create-api-token';
import { JwtModule } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';
import { ValidateApiToken } from 'src/app/use-cases/authentication/api-token/validade-api-token';
import { CreateProject } from 'src/app/use-cases/projects/create-project';
import { ProjectsController } from './controllers/projects.controller';
import { FindProjects } from 'src/app/use-cases/projects/find-projects';

@Module({
  imports: [
    JwtModule.registerAsync({
      useFactory: (configService: ConfigService) => ({
        global: true,
        secret: configService.get('JWT_SECRET'),
        signOptions: { expiresIn: '30d' },
      }),
      inject: [ConfigService],
    }),
    HttpModuleAxios,
    DatabaseModule,
  ],
  controllers: [AuthenticationController, ProjectsController],
  providers: [
    GenerateOAuth2RedirectUrl,
    GetUserFromOAuth2,
    CreateApiToken,
    ValidateApiToken,
    CreateProject,
    FindProjects,
  ],
})
export class HttpModule {}
