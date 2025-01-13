import type { Replace } from 'src/helpers/Replace';
import { generateUUID } from 'src/helpers/uuid';
import type { Entity } from './entity';

type Props = {
  identifier: string;
  name: string;
  enabled: boolean;
  updatedAt: Date;
  createdAt: Date;
};

export class Provider implements Entity {
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

  public get name(): string {
    return this.props.name;
  }

  public get identifier(): string {
    return this.props.identifier;
  }

  public get enabled(): boolean {
    return this.props.enabled;
  }

  public get updatedAt(): Date {
    return this.props.updatedAt;
  }

  public get createdAt(): Date {
    return this.props.createdAt;
  }

  public set name(name: string) {
    this.props.name = name;
  }

  public set identifier(identifier: string) {
    this.props.identifier = identifier;
  }

  public set enabled(enabled: boolean) {
    this.props.enabled = enabled;
  }

  public set updatedAt(updatedAt: Date) {
    this.props.updatedAt = updatedAt;
  }

  public set createdAt(createdAt: Date) {
    this.props.createdAt = createdAt;
  }
}
