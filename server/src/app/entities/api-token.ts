import type { Replace } from 'src/helpers/Replace';
import { generateUUID } from 'src/helpers/uuid';
import { Entity } from './entity';

type Props = {
  userId: string;
  enabled: boolean;
  expiresAt: Date;
  ipAddress: string | null;
  location: string | null;
  updatedAt: Date;
  createdAt: Date;
};

export class ApiToken implements Entity {
  private _id: string;
  private props: Props;

  constructor(
    _id: string | null,
    props: Replace<Props, { updatedAt?: Date; createdAt?: Date }>,
  ) {
    this._id = _id ? _id : generateUUID();
    this.props = {
      ...props,
      updatedAt: props.updatedAt ?? new Date(),
      createdAt: props.createdAt ?? new Date(),
    };
  }

  public get id() {
    return this._id;
  }

  public get userId() {
    return this.props.userId;
  }

  public get isEnabled() {
    return this.props.enabled;
  }

  public get expiresAt() {
    return this.props.expiresAt;
  }

  public get ipAddress() {
    return this.props.ipAddress;
  }

  public get location() {
    return this.props.location;
  }

  public get updatedAt() {
    return this.props.updatedAt;
  }

  public get createdAt() {
    return this.props.createdAt;
  }

  public set id(id: string) {
    this.id = id;
  }

  public set userId(userId: string) {
    this.props.userId = userId;
  }

  public set enabled(enabled: boolean) {
    this.props.enabled = enabled;
  }

  public set expiresAt(expiresAt: Date) {
    this.props.expiresAt = expiresAt;
  }

  public set ipAddress(ipAddress: string) {
    this.props.ipAddress = ipAddress;
  }

  public set location(location: string) {
    this.props.location = location;
  }

  public set updatedAt(updatedAt: Date) {
    this.props.updatedAt = updatedAt;
  }

  public set createdAt(createdAt: Date) {
    this.props.createdAt = createdAt;
  }
}
