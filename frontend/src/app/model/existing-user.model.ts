import { UserRole } from './user-role.model';

export interface ExistingUser {
  id: string;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  picture: string;
  token?: string;
  role?: UserRole;
  createdAt: string;
  createdBy?: number;
}
