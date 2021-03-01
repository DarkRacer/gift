import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SelectionService } from '../../services/selection.service';
import { AssortmentModel } from '../../model/assortment.model';
import { ProductModel } from '../../model/product.model';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-self-dialog',
  templateUrl: './self-dialog.component.html',
  styleUrls: ['./self-dialog.component.scss']
})
export class SelfDialogComponent implements OnInit {
  assortments: AssortmentModel[] = [];
  columns = ['name', 'price', 'function'];
  selection = new SelectionModel<ProductModel>(true, []);

  constructor(
    private readonly selectionService: SelectionService,
    @Inject(MAT_DIALOG_DATA) public data: ProductModel[]
  ) {}

  ngOnInit(): void {
    this.selectionService.selectSelf(this.data).subscribe((e) => {
      this.assortments = e;
    });
  }

  _product(row: any): ProductModel {
    return row as ProductModel;
  }
}
