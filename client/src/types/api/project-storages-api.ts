export type ProjectStorage = {
  id: string;
  bucketName: string;
  region: string;
  status: ProjectStorageStatus;
  updatedAt: Date;
  createdAt: Date;
};

export type ProjectStorageActivity = {
  id: string;
  operationData: string;
  operationType: ProjectStorageActivityOperationType;
  executedAt: Date;
  updatedAt: Date;
  createdAt: Date;
};

export type ProjectStorageActivityOperationType =
  | "CHECK_CREDENTIALS_FETCH"
  | "CHECK_CREDENTIALS_RESULT"
  | "CHECK_BUCKET"
  | "FIND_OBJECT"
  | "CREATE_OBJECT"
  | "UPDATE_OBJECT"
  | "DELETE_OBJECT"
  | "CONFIGURE_QUEUE"
  | "REMOVE_QUEUE";

export type ProjectStorageStatus = "VALID" | "INVALID";
