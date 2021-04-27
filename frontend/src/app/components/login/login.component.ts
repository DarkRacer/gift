import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {CurrentUserService} from "../../core/auth/current-user.service";
import {RefreshUserService} from "../../core/auth/refresh-user.service";
import { ActivatedRoute, Router } from '@angular/router';
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import { AuthResponseModel } from '../../model/auth-response.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  content: string | undefined;
  sid: string | undefined;
  url: string | undefined;

  constructor(
    private readonly currentUserService: CurrentUserService
  ) { }

  ngOnInit(): void {
      this.currentUserService.getSid().subscribe(sid => {
        console.log(sid);

        this.sid = sid as string;

        this.url = "https://search-gift-backend.herokuapp.com/oauth2/authorization/vk?" +
          "redirect_uri=https://search-gift-frontend.herokuapp.com/user&sid="+ this.sid;
      });
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
