import type { Replace } from 'src/helpers/Replace';
import type { Entity } from './entity';
import { generateUUID } from 'src/helpers/uuid';

type Props = {
  name: string;

  updatedAt: Date;
  createdAt: Date;
};

export class Project implements Entity {
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

  public get name() {
    return this.props.name;
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
      name: this.props.name,
      updatedAt: this.props.updatedAt,
      createdAt: this.props.createdAt,
    };
  }
}
