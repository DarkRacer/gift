import { AfterViewInit, ChangeDetectorRef, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SelectionService } from '../../services/selection.service';
import { AssortmentModel } from '../../model/assortment.model';
import { ProductModel } from '../../model/product.model';
import { SelectionModel } from '@angular/cdk/collections';
import { CategoryModel } from '../../model/category.model';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { merge, startWith, switchMap, tap } from 'rxjs/operators';
import { MatSort } from '@angular/material/sort';
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
  dataSource = new MatTableDataSource<ProductModel>();
  resultsLength = 0;

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private readonly selectionService: SelectionService,
    @Inject(MAT_DIALOG_DATA) public data: ProductModel[],
    private cdr: ChangeDetectorRef
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
      this.dataSource.data = this.products;
      this.cdr.detectChanges();
      this.resultsLength = this.products.length;
      this.dataSource.paginator = this.paginator;
    })
  }
}
