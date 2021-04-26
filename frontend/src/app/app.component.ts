import {Component, OnInit} from '@angular/core';
import {SelectionService} from "./services/selection.service";
import {AnonymousUserImpl, CurrentUserService} from "./core/auth/current-user.service";
import {Router} from "@angular/router";
import {ExistingUser} from "./model/existing-user.model";
import {BehaviorSubject} from "rxjs";
import {CurrentUser} from "./core/auth/current-user.model";

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
  title = 'Gift';

  constructor(
    private readonly selectionService: SelectionService,
    private readonly router: Router,
    private readonly currentUserService: CurrentUserService
  ) { }

  ngOnInit(): void {
        this.isLoggedIn = !!localStorage.getItem('auth_token');

        if (this.isLoggedIn) {
          // @ts-ignore
          this.user = this.user$.getValue();
        }
    }

  logout() {
    this.currentUserService.user$.next(new AnonymousUserImpl());
    localStorage.clear();
    window.location.reload();
  }

  handleLoginClick() {
    this.router.navigate(["/login"]).then(() => {
      window.location.reload();
    });
  }
}
