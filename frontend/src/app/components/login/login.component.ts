import {Component, OnInit} from '@angular/core';
import {CurrentUserService} from "../../core/auth/current-user.service";
import {HttpClient} from "@angular/common/http";
import { SelectionService } from '../../services/selection.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RefreshUserService } from '../../core/auth/refresh-user.service';
import { AuthResponseModel } from '../../model/auth-response.model';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  content: string | undefined;
  uuid: string | undefined;
  url: string | undefined;

  authResponse: AuthResponseModel[] = [];

  constructor(
    private readonly currentUserService: CurrentUserService,
    private readonly selectionService: SelectionService,
    private router: Router,
    private readonly refreshUserService: RefreshUserService,
    private route: ActivatedRoute,
    private http: HttpClient,
  ) { }

  ngOnInit(): void {
    this.url = "http://localhost:8080/oauth2/authorization/vk?" +
      "redirect_uri=http://localhost:8000/login&uuid="+ this.currentUserService.getUuid();

    const code: string | null = this.route.snapshot.queryParamMap.get('code');
    console.log("code " + code);
    if (code != null) {
      this.currentUserService.getAuth(code, this.currentUserService.getUuid()).subscribe(authResponse => {
        this.authResponse = authResponse;
      });

      localStorage.setItem('auth_token', this.authResponse[0].token)
      localStorage.setItem('user_id', String(this.authResponse[0].userId));

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
