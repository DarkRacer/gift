import {Component, Injectable, OnInit} from '@angular/core';
import { UserVkModel } from '../../model/userVk.model';
import { SelectionService } from '../../services/selection.service';
import { Router } from '@angular/router';
import {CurrentUserService} from "../../core/auth/current-user.service";
import {CookieService} from "ngx-cookie-service";
import {RefreshUserService} from "../../core/auth/refresh-user.service";
import {ExistingUser} from "../../model/existing-user.model";
import {patchTsGetExpandoInitializer} from "@angular/compiler-cli/ngcc/src/packages/patch_ts_expando_initializer";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  user$ = this.currentUserService.user$;
  users: ExistingUser[] = [];

  constructor(private readonly selectionService: SelectionService, private router: Router, private readonly currentUserService: CurrentUserService,) {}

  ngOnInit(): void {
    console.log(this.user$.getValue());
    // @ts-ignore
    this.users.push(this.user$.getValue());
  }
}
