import { APP_INITIALIZER, Injectable, Provider } from '@angular/core';
import { BehaviorSubject, Observable, of, Subscription, throwError } from 'rxjs';
import { HttpBackend, HttpClient } from '@angular/common/http';
import { catchError, switchMap, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Role } from './role.model';
import { Anonymous, CurrentUser, LoggedUser } from './current-user.model';

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
  providedIn: 'root'
})
export class CurrentUserService {
  readonly user$ = new BehaviorSubject<CurrentUser>(new AnonymousUserImpl());
  private http: HttpClient;

  constructor(private httpClient: HttpBackend, private router: Router) {
    this.http = new HttpClient(httpClient);
  }

  login(provider: string): Observable<any> {
    return this.http.get<any>(`api/oauth2/authorization/${provider}?redirect_uri=http://localhost:4200/user`);
  }

  logout(): Observable<void> {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('user_id');
    return this.http
      .post<void>(`api/auth/logout`, undefined)
      .pipe(tap(() => this.user$.next(new AnonymousUserImpl())));
  }



  setUser(user: CurrentUser): void {
    this.user$.next(user);
  }

  logIn(): boolean {
    return localStorage.getItem('auth_token') !== null;
  }
}
