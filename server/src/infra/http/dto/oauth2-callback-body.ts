import { IsNotEmpty } from 'class-validator';

export class OAuth2CallbackBody {
  @IsNotEmpty()
  code: string;
}
