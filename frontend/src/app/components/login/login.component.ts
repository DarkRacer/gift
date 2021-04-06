import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {CurrentUserService} from "../../core/auth/current-user.service";
import {RefreshUserService} from "../../core/auth/refresh-user.service";
import {Router} from "@angular/router";
import {map, switchMap} from "rxjs/operators";
import {Jsonp} from "@angular/http";
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  content: string | undefined;

  constructor(
    private readonly currentUserService: CurrentUserService,
    private readonly getUserService: RefreshUserService,
    private readonly router: Router,
    private cookie: CookieService,
    private readonly refreshUserService: RefreshUserService
  ) { }

  ngOnInit(): void {
    if (this.cookie.check("Authorization") && this.cookie.check("user_id")) {
      localStorage.setItem('auth_token', this.cookie.get("Authorization"))
      localStorage.setItem('user_id', this.cookie.get("user_id"));
      this.cookie.deleteAll();

      this.refreshUserService.refreshCurrentUser().subscribe();
      this.router.navigate(["/user"]).then(() => {
        window.location.reload();
      });
    }
  }

  login(provider: string): void {
    this.currentUserService
      .login(provider)
      .subscribe(
        (response: any) => {
            this.content = response;
        },
        (error) => {
          console.error('Error', error);
        }
      );
  }
}
