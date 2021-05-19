import {Component, OnInit} from '@angular/core';
import {SelectionService} from "./services/selection.service";
import {AnonymousUserImpl, CurrentUserService} from "./core/auth/current-user.service";
import {Router} from "@angular/router";
import {ExistingUser} from "./model/existing-user.model";
import { UserRole } from './model/user-role.model';
import { AdminService } from './services/admin.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  user$ = this.currentUserService.user$;
  // @ts-ignore
  user: ExistingUser;
  isLoggedIn = false;
  isAdmin = false;
  title = 'Gift';

  constructor(
    private readonly selectionService: SelectionService,
    private readonly router: Router,
    private readonly currentUserService: CurrentUserService,
    private readonly adminService: AdminService
  ) { }

  ngOnInit(): void {
        this.isLoggedIn = !!localStorage.getItem('auth_token');

        if (this.isLoggedIn) {
          // @ts-ignore
          this.user = this.user$.getValue();

          this.adminService.getRoles(localStorage.getItem('user_id')).subscribe(userRole => {
            if (userRole.indexOf(UserRole.ADMIN) != -1) {
              this.isAdmin = true;
            }
          })
        }
    }

  logout() {
    this.currentUserService.user$.next(new AnonymousUserImpl());
    localStorage.clear();
    window.location.reload();
  }
}
