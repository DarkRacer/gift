import {ChangeDetectorRef, Component, Injectable, OnInit} from '@angular/core';
import { SelectionService } from '../../services/selection.service';
import { Router } from '@angular/router';
import {CurrentUserService} from "../../core/auth/current-user.service";
import {ExistingUser} from "../../model/existing-user.model";
import {ProductModel} from "../../model/product.model";
import {SelectionsHistoryModel} from "../../model/selections-history.model";
import {SelfDialogComponent} from "../self-dialog/self-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ProductsSelectionDialogComponent} from "../products-selection-dialog/products-selection-dialog.component";

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

  constructor(private readonly selectionService: SelectionService,
              private router: Router,
              private readonly currentUserService: CurrentUserService,
              public dialog: MatDialog) {}

  ngOnInit(): void {
    // @ts-ignore
    this.users.push(this.user$.getValue());
    this.loadWish(this.users[0].id);
    this.loadHistory(this.users[0].id);
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
}
