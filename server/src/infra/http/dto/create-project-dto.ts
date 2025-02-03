import { IsString, Length } from 'class-validator';

export class CreateProjectDto {
  @IsString()
  @Length(4, 32, { message: 'Invalid length' })
  name: string;
}
