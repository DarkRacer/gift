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
  uuid: string | undefined;
  url: string | undefined;

  constructor(
    private readonly currentUserService: CurrentUserService,
    private readonly selectionService: SelectionService,
    private http: HttpClient,
  ) { }

  ngOnInit(): void {
    const req = new XMLHttpRequest();
    // @ts-ignore
    req.open('GET', document.location, false);
    req.send(null);
    console.log(req.getAllResponseHeaders().toLowerCase());
    const header = req.getAllResponseHeaders().toLowerCase();
    const headers: string[] = header.split(";");
    console.log(headers);

    headers.forEach((currentValue) => {
        if(currentValue.includes("uuid")) {
          this.uuid = currentValue.replace("/uuid: /gi", "");
        }
    })

    this.url = "https://search-gift-backend.herokuapp.com/oauth2/authorization/vk?" +
      "redirect_uri=https://search-gift-frontend.herokuapp.com/user&uuid="+ this.uuid;
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
