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
  url: string | undefined;

  constructor(
    private readonly currentUserService: CurrentUserService,
    private readonly selectionService: SelectionService,
    private router: Router,
    private readonly refreshUserService: RefreshUserService,
    private route: ActivatedRoute,
    private http: HttpClient,
  ) { }

  ngOnInit(): void {
    const code: string | null = this.route.snapshot.queryParamMap.get('code');

    if (code != null) {
      this.currentUserService.getAuth(code, localStorage.getItem("UUID") as string).subscribe(authResponse => {
        localStorage.setItem('auth_token', authResponse.token)
        localStorage.setItem('user_id', String(authResponse.userId));

        this.refreshUserService.refreshCurrentUser().subscribe();
        this.router.navigate(["/user"]).then(() => {
          window.location.reload();
        });
      });
    } else {
      const uuid: string | null = this.currentUserService.getUuid();

      this.url = "https://search-gift-backend.herokuapp.com/oauth2/authorization/vk?" +
        "redirect_uri=https://search-gift-frontend.herokuapp.com/login&uuid="+ uuid;

      if (typeof uuid === 'string') {
        localStorage.setItem('UUID', uuid);
      }
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
