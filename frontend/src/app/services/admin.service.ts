import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private readonly http: HttpClient) { }

  findAllCategoryWords () : Observable<any> {
    return this.http.get(`api/categories/all`);
  }
}
