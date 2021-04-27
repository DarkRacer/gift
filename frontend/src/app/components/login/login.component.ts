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
  authResponse: AuthResponseModel[] = [];
  sid: string | undefined;
  url: string | undefined;

  constructor(
    private readonly currentUserService: CurrentUserService,
    private readonly getUserService: RefreshUserService,
    private readonly router: Router,
    private cookie: CookieService,
    private readonly refreshUserService: RefreshUserService,
    private readonly http: HttpClient,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const code: string | null = this.route.snapshot.queryParamMap.get('code');

    console.log("code " + code);
    if (code != null) {
      this.currentUserService.getAuth(code, this.sid).subscribe(authResponse => {
        this.authResponse = authResponse;
      });

      console.log(this.authResponse[0].userId);

      localStorage.setItem('test', "test");
      localStorage.setItem('auth_token', this.authResponse[0].token)
      localStorage.setItem('user_id', String(this.authResponse[0].userId));

      this.refreshUserService.refreshCurrentUser().subscribe();
      this.router.navigate(["/user"]).then(() => {
        window.location.reload();
      });
    } else {
      // @ts-ignore
      this.http.get('api/auth/sid').subscribe(sid => {
        console.log(sid);

        this.sid = sid as string;
      });

      this.url = "https://search-gift-backend.herokuapp.com/oauth2/authorization/vk?" +
        "redirect_uri=https://search-gift-frontend.herokuapp.com/user&sid="+ this.sid;
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
