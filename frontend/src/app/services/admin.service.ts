import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryWordsModel } from '../model/category-words.model';
import { UserAndRoleModel } from '../model/user-and-role.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private readonly http: HttpClient) { }

  findAllCategoryWords () : Observable<any> {
    return this.http.get(`api/categories/all`);
  }

  findAllCategory () : Observable<any> {
    return this.http.get(`api/categories/category/all`);
  }

  save(selectedWordCategories: CategoryWordsModel | undefined) : Observable<any> {
    return this.http.post(`api/categories/save`, selectedWordCategories);
  }

  findAllUser(id: string | null) : Observable<any> {
    return this.http.get(`api/user/all/${id}`);
  }

  saveAdmin(userAndRoleModel: UserAndRoleModel) {
    return this.http.post(`api/user/saveAdmin`, userAndRoleModel);
  }

  deleteAdmin(userAndRoleModel: UserAndRoleModel) {
    return this.http.post(`api/user/deleteAdmin`, userAndRoleModel);
  }

  getRoles(id: string | null): Observable<any> {
    return this.http.get(`api/user/roles/${id}`);
  }
}
