import { Component, OnInit } from '@angular/core';
import {CurrentUserService} from "../../core/auth/current-user.service";
import {RefreshUserService} from "../../core/auth/refresh-user.service";
import {Router} from "@angular/router";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(
    private readonly currentUserService: CurrentUserService,
    private readonly getUserService: RefreshUserService,
    private readonly router: Router
  ) { }

  ngOnInit(): void {
  }

  login(provider: string): void {
    console.log(provider);
    this.currentUserService
      .login(provider)
      .subscribe(
        (response: any) => {
          console.log(response.headers.get("Authorization"));
          console.log(response);
        },
        (error) => {
          console.error('Error', error);
        }
      );
    switchMap(() => this.getUserService.refreshCurrentUser());
  }
}
