import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductModel } from '../model/product.model';
import { CategoryModel } from '../model/category.model';

@Injectable({
  providedIn: 'root'
})
export class SelectionService {
  service = 'http://localhost:8080';

  constructor(private readonly http: HttpClient) {}

  findGroups(userId: string): Observable<any> {
    return this.http.post(`${this.service}/gifts/find/groups`, userId);
  }

  findUser(userUrl: string): Observable<any> {
    return this.http.post(`${this.service}/gifts/find/user`, userUrl);
  }

  findCategories(topics: string[]): Observable<any> {
    return this.http.post(`${this.service}/categories/find`, topics);
  }

  findProducts(categories: CategoryModel[]): Observable<any> {
    return this.http.post(`${this.service}/products/find`, categories);
  }

  save(recipient: string, products: ProductModel[]): Observable<any> {
    const body = { recipient, products };
    return this.http.post(`${this.service}/transaction/save`, body);
  }

  selectSelf(products: ProductModel[]): Observable<any> {
    return this.http.post(`${this.service}/gifts/self`, products);
  }
}
