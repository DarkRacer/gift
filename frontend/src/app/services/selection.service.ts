import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductModel } from '../model/product.model';
import { CategoryModel } from '../model/category.model';
import {Jsonp} from "@angular/http";

@Injectable({
  providedIn: 'root'
})
export class SelectionService {
  service = 'http://localhost:8080';

  constructor(private readonly http: HttpClient, private readonly jsonp: Jsonp) {}

  findGroups(userId: string): Observable<any> {
    return this.http.post(`api/gifts/find/groups`, userId);
  }

  findUser(userUrl: string): Observable<any> {
    return this.http.post(`api/gifts/find/user`, userUrl);
  }

  findCategories(topics: string[]): Observable<any> {
    return this.http.post(`api/categories/find`, topics);
  }

  findProducts(categories: CategoryModel[]): Observable<any> {
    return this.http.post(`api/products/find`, categories);
  }

  save(recipient: string, products: ProductModel[]): Observable<any> {
    const body = { recipient, products };
    return this.http.post(`api/transaction/save`, body);
  }

  login(): Observable<any> {
    return this.http.get(`api/oauth2/authorization/vk`);
  }

  loginVk(): Observable<any>  {
    return this.http.jsonp('api/oauth2/authorization/vk', "callback");
  }
  selectSelf(products: ProductModel[]): Observable<any> {
    return this.http.post(`${this.service}/gifts/self`, products);
  }
}
