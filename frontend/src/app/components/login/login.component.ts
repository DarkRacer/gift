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
  sid: SidModel[] = [];
  url: string | undefined;

  constructor(
    private readonly currentUserService: CurrentUserService,
    private readonly selectionService: SelectionService,
    private http: HttpClient,
  ) { }

  ngOnInit(): void {
    this.selectionService.getSid(" ").subscribe(sid => {
        console.log(sid);

        this.sid = sid;

        this.url = "https://search-gift-backend.herokuapp.com/oauth2/authorization/vk?" +
          "redirect_uri=https://search-gift-frontend.herokuapp.com/user&sid="+ this.sid[0].sid;
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
