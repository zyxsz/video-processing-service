import type { Replace } from 'src/helpers/Replace';
import { generateUUID } from 'src/helpers/uuid';
import type { Entity } from './entity';

type Props = {
  username: string;
  email: string;
  isEmailVerifiedByProvider: boolean;
  avatar: string;
  providerId: string | null;
  updatedAt: Date;
  createdAt: Date;
};

export class User implements Entity {
  private _id: string;
  private props: Props;

  constructor(
    _id: string | null,
    props: Replace<Props, { updatedAt?: Date; createdAt?: Date }>,
  ) {
    this._id = _id ?? generateUUID();
    this.props = {
      ...props,
      updatedAt: props.updatedAt ?? new Date(),
      createdAt: props.createdAt ?? new Date(),
    };
  }

  public get id() {
    return this._id;
  }

  public get email() {
    return this.props.email;
  }

  public get username() {
    return this.props.username;
  }

  public get avatar() {
    return this.props.avatar;
  }

  public get isEmailVerifiedByProvider() {
    return this.props.isEmailVerifiedByProvider;
  }

  public get providerId() {
    return this.props.providerId;
  }

  public get updatedAt() {
    return this.props.updatedAt;
  }

  public get createdAt() {
    return this.props.createdAt;
  }

  public toJson() {
    return {
      id: this._id,
      isEmailVerifiedByProvider: this.props.isEmailVerifiedByProvider,
      email: this.props.email,
      username: this.props.username,
      avatar: this.props.avatar,
      providerId: this.props.providerId,
      updatedAt: this.props.updatedAt,
      createdAt: this.props.createdAt,
    };
  }
}
