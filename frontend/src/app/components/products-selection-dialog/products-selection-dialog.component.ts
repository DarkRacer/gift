import {Component, Inject, OnInit} from '@angular/core';
import {SelectionService} from "../../services/selection.service";
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import {SelectionsHistoryModel} from "../../model/selections-history.model";
import { ProductModel } from '../../model/product.model';
import { SelfDialogComponent } from '../self-dialog/self-dialog.component';

@Component({
  selector: 'app-products-selection-dialog',
  templateUrl: './products-selection-dialog.component.html',
  styleUrls: ['./products-selection-dialog.component.scss']
})
export class ProductsSelectionDialogComponent implements OnInit {

  constructor(
    private readonly selectionService: SelectionService,
    @Inject(MAT_DIALOG_DATA) public data: SelectionsHistoryModel,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
  }

  deleteProduct(product: any) {
    this.selectionService.deleteSelectionsHistory(this.data.transactionId, product.id).subscribe();
    window.location.reload();
  }

  addProduct() {
    const dialogRef = this.dialog.open(SelfDialogComponent, {
      width: '80%',
      height: '80%',
      data: this.data.gifts
    });

    dialogRef.afterClosed().subscribe((result : ProductModel) => {
      if (result) {
        this.selectionService.saveSelectionsHistory(this.data.transactionId, result.id).subscribe();
        window.location.reload();
      }
    });
  }

}
