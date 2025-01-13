import type {
  ProjectStorageActivityOperationType,
  ProjectStorageStatus,
} from "@/types/api/project-storages-api";

export function formatProjectStorageProvider() {
  return "Amazon S3";
}

export function formatProjectStorageStatus(status: ProjectStorageStatus) {
  switch (status) {
    case "VALID":
      return "Armazenamento verificado";
    case "INVALID":
      return "Armazenamento inválido";
  }

  return "";
}

export function formatProjectStorageActivityOperationType(
  type: ProjectStorageActivityOperationType
) {
  switch (type) {
    case "CHECK_BUCKET":
      return "Checando bucket";
    case "CHECK_CREDENTIALS_FETCH":
      return "Checando credenciais";
    case "CHECK_CREDENTIALS_RESULT":
      return "Credenciais checada";
    case "CONFIGURE_QUEUE":
      return "Fila configurada";
    case "CREATE_OBJECT":
      return "Objeto criado";
    case "DELETE_OBJECT":
      return "Objeto deletado";
    case "FIND_OBJECT":
      return "Procurando objeto";
    case "REMOVE_QUEUE":
      return "Removendo fila";
    case "UPDATE_OBJECT":
      return "Atualizando objeto";

    default:
      return "Não encontrado";
  }

  return "Não encontrado";
}
