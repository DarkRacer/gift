import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductModel } from '../model/product.model';
import { CategoryModel } from '../model/category.model';

@Injectable({
  providedIn: 'root'
})
export class SelectionService {

  constructor(private readonly http: HttpClient) {}

  findGroups(userId: string): Observable<any> {
    return this.http.post(`api/gifts/find/groups`, userId);
  }

  findUser(userUrl: string): Observable<any> {
    return this.http.post(`api/gifts/find/user`, userUrl);
  }

  findCategories(topics: string[]): Observable<any> {
    return this.http.post(`api/categories/find`, topics);
  }

  findAllCategory () : Observable<any> {
    return this.http.get(`api/categories/category/all`);
  }

  findProducts(categories: CategoryModel[], userId: number): Observable<any> {
    const body = {categories, userId};
    return this.http.post(`api/products/find`, body);
  }

  save(recipient: string, products: ProductModel[], sender: number): Observable<any> {
    const body = { recipient, products, sender };
    return this.http.post(`api/transaction/save`, body);
  }

  login(): Observable<any> {
    return this.http.get(`api/oauth2/authorization/vk`);
  }

  selectCategories(): Observable<any> {
    return this.http.get(`api/gifts/self/categories`);
  }

  loadWish(userId: number): Observable<any> {
    return this.http.post(`api/transaction/wish`, userId);
  }

  loadHistory(userId: number): Observable<any> {
    return this.http.post(`api/transaction/history`, userId);
  }

  delete(id : number, productId: number): Observable<any> {
    const body = { id, productId };
    return this.http.post(`api/transaction/wish/delete`, body);
  }

  saveWish (id : number, productId: number): Observable<any> {
    const body = {id, productId};
    return this.http.post(`api/transaction/wish/save`, body);
  }

  deleteSelectionsHistory(id : number, productId: number): Observable<any> {
    const body = { id, productId };
    return this.http.post(`api/transaction/selectionsHistory/delete`, body);
  }

  saveSelectionsHistory (id : number, productId: number): Observable<any> {
    const body = {id, productId};
    return this.http.post(`api/transaction/selectionsHistory/save`, body);
  }

  findProduct(categories: any): Observable<any> {
    return this.http.post(`api/products/findByCategories`, categories);
  }

  saveUser(id: number, firstName: string, lastName:string, email: string) {
    const body = {id, firstName, lastName, email};
    return this.http.post(`api/user/save`, body);
  }
}
