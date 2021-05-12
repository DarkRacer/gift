import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {first} from "rxjs/operators";
import { CurrentUserService } from '../auth/current-user.service';
import { UserRole } from '../../model/user-role.model';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardGuard implements CanActivate {

  constructor(private router: Router,
              private readonly currentUserService: CurrentUserService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    first();
    const roles: string[] = route.data['roles'];

    if (localStorage.getItem('auth_token')) {
      if (roles != undefined && roles.indexOf(UserRole.ADMIN) != -1) {
        this.currentUserService.getRoles(localStorage.getItem('user_id')).subscribe(userRole => {
          if (userRole.indexOf(UserRole.ADMIN) != -1) {
            return true;
          } else {
            this.router.navigate(['/forbidden']);
            return false;
          }
        });
      }
      return true;
    }

    this.router.navigate(['/login']);
    return false;
  }

}
