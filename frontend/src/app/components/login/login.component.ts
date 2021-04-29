import {Component, OnInit} from '@angular/core';
import {CurrentUserService} from "../../core/auth/current-user.service";
import {HttpClient} from "@angular/common/http";
import { SidModel } from '../../model/sid.model';
import { SelectionService } from '../../services/selection.service';
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
    private readonly currentUserService: CurrentUserService,
    private readonly selectionService: SelectionService,
    private http: HttpClient,
  ) { }

  ngOnInit(): void {
    console.log(document.cookie.length);
    const req = new XMLHttpRequest();
    // @ts-ignore
    req.open('GET', document.location, false);
    req.send(null);
    console.log(req.getAllResponseHeaders().toLowerCase());
    const headers = req.getAllResponseHeaders().toLowerCase();
    alert(headers);
    const cookies: string[] = document.cookie.split(";");
    console.log(document.cookie.split(";"));
    console.log(cookies);

    cookies.forEach((currentValue) => {
        if(currentValue.includes("connect.sid")) {
          this.sid = currentValue.replace("/connect.sid=/gi", "");
        }
    })

    this.url = "https://search-gift-backend.herokuapp.com/oauth2/authorization/vk?" +
      "redirect_uri=https://search-gift-frontend.herokuapp.com/user&sid="+ this.sid;
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
