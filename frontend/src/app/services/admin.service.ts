import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryWordsModel } from '../model/category-words.model';

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


  save(selectedWordCategories: CategoryWordsModel | undefined) {
    return this.http.post(`api/categories/save`, selectedWordCategories);
  }
}
