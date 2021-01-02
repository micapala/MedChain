import { Role } from "./role";

export class User {
  login: string;
  name: string;
  surname: string;
  attrs: string[];
  gender: string;

  role: Role;
}
