import type { Replace } from 'src/helpers/Replace';
import type { Entity } from './entity';

type Props = {
  roleId: string;

  status: 'REQUESTED' | 'ACCEPTED' | 'DENIED';

  attachedAt: Date | null;
  updatedAt: Date;
  requestedAt: Date;
};

export class UserProject implements Entity {
  private _userId: string;
  private _projectId: string;

  private props: Props;

  constructor(
    userId: string,
    projectId: string,
    props: Replace<
      Props,
      { updatedAt?: Date; requestedAt?: Date; attachedAt?: Date }
    >,
  ) {
    this._userId = userId;
    this._projectId = projectId;
    this.props = {
      ...props,
      attachedAt: props.attachedAt ?? null,
      updatedAt: props.updatedAt ?? new Date(),
      requestedAt: props.requestedAt ?? new Date(),
    };
  }

  public get projectId() {
    return this._projectId;
  }

  public get userId() {
    return this._userId;
  }

  public get status() {
    return this.props.status;
  }

  public get roleId() {
    return this.props.roleId;
  }

  public get attachedAt() {
    return this.props.attachedAt;
  }

  public get updatedAt() {
    return this.props.updatedAt;
  }

  public get requestedAt() {
    return this.props.requestedAt;
  }
}
