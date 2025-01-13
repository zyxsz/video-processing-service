import { Module } from '@nestjs/common';
import { DatabaseModule } from './infra/database/database.module';
import { HttpModule } from './infra/http/http.module';
import { ConfigModule } from '@nestjs/config';
import { HttpModule as HttpModuleAxios } from '@nestjs/axios';

@Module({
  imports: [
    ConfigModule.forRoot({ isGlobal: true, envFilePath: '.development.env' }),

    HttpModuleAxios.register({ global: true }),
    DatabaseModule,
    HttpModule,
  ],
})
export class AppModule {}
