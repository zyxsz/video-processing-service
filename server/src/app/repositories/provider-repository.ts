import type { Provider } from '../entities/provider';

export abstract class ProviderRepository {
  abstract create(provider: Provider): Promise<void>;
  abstract findAllEnabled(): Promise<Provider[]>;
  abstract findById(id: string): Promise<Provider>;
}
