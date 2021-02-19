import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductModel } from '../model/product.model';

@Injectable({
  providedIn: 'root'
})
export class SelectionService {
  service = 'http://localhost:8080';

  constructor(private readonly http: HttpClient) {}

  find(userUrl: string): Observable<any> {
    return this.http.post(`${this.service}/words/find`, userUrl);
  }

  save(recipient: string, products: ProductModel[]): Observable<any> {
    const body = { recipient, products };
    return this.http.post(`${this.service}/transaction/save`, body);
  }
}
