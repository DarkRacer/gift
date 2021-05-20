import { Component,  OnInit} from '@angular/core';
import { SelectionService } from '../../services/selection.service';
import { ActivatedRoute, Router } from '@angular/router';
import {CurrentUserService} from "../../core/auth/current-user.service";
import {ExistingUser} from "../../model/existing-user.model";
import {ProductModel} from "../../model/product.model";
import {SelectionsHistoryModel} from "../../model/selections-history.model";
import {SelfDialogComponent} from "../self-dialog/self-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ProductsSelectionDialogComponent} from "../products-selection-dialog/products-selection-dialog.component";
import { RefreshUserService } from '../../core/auth/refresh-user.service';
import { HttpClient } from '@angular/common/http';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  user$ = this.currentUserService.user$;
  users: ExistingUser[] = [];
  wishProducts: ProductModel[] = [];
  selectionsHistories: SelectionsHistoryModel[] = [];
  userGroup: FormGroup = new FormGroup({
    userFirstName: new FormControl('', [
      Validators.required,
      Validators.pattern('^[a-zA-Zа-яА-Я]*$')
    ]),
    userLastName: new FormControl('', [
      Validators.required,
      Validators.pattern('^[a-zA-Zа-яА-Я]*$')
    ]),
    userEmail: new FormControl('', [
      Validators.email
    ])
  });

  constructor(private readonly selectionService: SelectionService,
              private router: Router,
              private readonly refreshUserService: RefreshUserService,
              private readonly currentUserService: CurrentUserService,
              private route: ActivatedRoute,
              private http: HttpClient,
              public dialog: MatDialog) {}

  ngOnInit(): void {
    // @ts-ignore
    this.users.push(this.user$.getValue());
    this.loadWish(this.users[0].id);
    this.loadHistory(this.users[0].id);

    this.userGroup.controls.userFirstName.setValue(this.users[0].firstName);
    this.userGroup.controls.userLastName.setValue(this.users[0].lastName);
    if (this.users[0].email) {
      this.userGroup.controls.userEmail.setValue(this.users[0].email);
    }
  }

  loadWish(userId: number): void {
    this.selectionService.loadWish(userId).subscribe((e) => {
      this.wishProducts = e;
    })
  }

  loadHistory(userId: number): void {
    this.selectionService.loadHistory(userId).subscribe((e) => {
        this.selectionsHistories = e
      })
  }

  openGiftDialog(selectionsHistory: SelectionsHistoryModel) {
    const dialogRef = this.dialog.open(ProductsSelectionDialogComponent, {
      width: '60%',
      height: '60%',
      data: selectionsHistory
    });

    // dialogRef.afterClosed().subscribe((result) => {
    //   window.location.reload();
    // });
  }

  deleteWish(wishProduct: ProductModel) {
      this.selectionService.delete(this.users[0].id, wishProduct.id).subscribe();
      window.location.reload();
  }

  addWish(): void {
    const dialogRef = this.dialog.open(SelfDialogComponent, {
      width: '80%',
      height: '80%',
      data: this.wishProducts
    });

    dialogRef.afterClosed().subscribe((result : ProductModel) => {
      if (result) {
        this.selectionService.saveWish(this.users[0].id, result.id).subscribe();
        window.location.reload();
      }
    });
  }

  saveUser() {
    this.selectionService.saveUser(this.users[0].id, this.userGroup.controls.userFirstName.value,
      this.userGroup.controls.userLastName.value, this.userGroup.controls.userEmail.value).subscribe( e => {
        window.location.reload();
    });
  }
}
