import { AfterViewInit, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SelectionService } from '../../services/selection.service';
import { AssortmentModel } from '../../model/assortment.model';
import { ProductModel } from '../../model/product.model';
import { SelectionModel } from '@angular/cdk/collections';
import { CategoryModel } from '../../model/category.model';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
@Component({
  selector: 'app-self-dialog',
  templateUrl: './self-dialog.component.html',
  styleUrls: ['./self-dialog.component.scss']
})
export class SelfDialogComponent implements OnInit {
  categories: CategoryModel[] = [];
  categoryCtrl = new FormControl();
  columns = ['name', 'price', 'function'];
  selection = new SelectionModel<ProductModel>(true, []);
  products: ProductModel[] = [];
  resultsLength = 0;
  pageSize = 5;

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  constructor(
    private readonly selectionService: SelectionService,
    @Inject(MAT_DIALOG_DATA) public data: ProductModel[]
  ) {}

  ngOnInit(): void {
    this.selectionService.findAllCategory().subscribe((e) => {
      this.categories = e;
    });
  }

  _product(row: any): ProductModel {
    return row as ProductModel;
  }

  findProduct() {
    this.selectionService.findProduct(this.categoryCtrl.value).subscribe(e => {
      this.products = e;
      this.resultsLength = this.products.length;
    })
  }
}
