import {  Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpBackend, HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Role } from './role.model';
import { Anonymous, CurrentUser, LoggedUser } from './current-user.model';
import {Jsonp} from "@angular/http";

interface ApiProfile {
  username: string;
  roles: [Role];
}

export class AnonymousUserImpl implements Anonymous {
  readonly authenticated: false = false;

  hasRole(role: Role): boolean {
    return false;
  }
}

export class CurrentUserImpl implements LoggedUser {
  readonly authenticated: true = true;
  readonly username = this.profile.username;

  private roles: Set<Role> = new Set(this.profile.roles);

  constructor(readonly profile: ApiProfile) {}

  hasRole(role: Role): boolean {
    return this.roles.has(role);
  }
}

@Injectable({
  providedIn: `root`
})
export class CurrentUserService {
  user$: BehaviorSubject<CurrentUser> = new BehaviorSubject<CurrentUser>(new AnonymousUserImpl());
  private http: HttpClient;

  constructor(private httpClient: HttpBackend, private router: Router, private http1: HttpClient, private jsonp: Jsonp) {
    this.http = new HttpClient(httpClient);
  }

   login(provider: string) {

    return this.http.post(`api/oauth2/authorization/vk?redirect_uri=https://search-gift-frontend.herokuapp.com/user`,
      "" , { responseType: 'text' });

  }

  o (data: any) {
    data.json();
    console.log(data.json())
    console.log(data);
  }

  setUser(user: CurrentUser): void {
    this.user$.next(user);
  }

  getAuth(code: string, uuid: string | undefined): Observable<any> {
    const body = { code, uuid };
    return this.http.post(`api/auth/code`, body, {headers: new HttpHeaders({
        'Content-Type': 'application/json'})});
  }

  getUuid(): string | null {
    const req = new XMLHttpRequest();
    // @ts-ignore
    req.open('GET', document.location, false);
    req.send(null);
    return req.getResponseHeader("Uuid");
  }
}
