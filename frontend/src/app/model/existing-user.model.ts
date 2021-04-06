import { UserRole } from './user-role.model';

export interface ExistingUser {
  id: number;
  providerUserId: number;
  firstName: string;
  lastName: string;
  picture: string;
  provider: string;
  email: string | null;
  name: string;
  token?: string;
  role?: UserRole;
  createdAt?: string;
  createdBy?: number;
}
