import { Component, Inject, Injectable, OnInit } from '@angular/core';
import { SelectionService } from '../../services/selection.service';
import { ProductModel } from '../../model/product.model';
import { FormControl, Validators } from '@angular/forms';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog } from '@angular/material/dialog';
import { SaveDialogComponent } from '../save-dialog/save-dialog.component';

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.scss']
})
export class SelectionComponent implements OnInit {
  columns = ['id', 'name', 'description', 'price', 'catalogUrl', 'function'];
  products: ProductModel[] = [];
  count = 0;
  userUrl = new FormControl('', [
    Validators.required,
    Validators.pattern(/https?:\/\/(www\.)?vk\.com\//i)
  ]);
  selectProducts: ProductModel[] = [];
  selection = new SelectionModel<ProductModel>(true, []);

  constructor(private readonly selectionService: SelectionService, public dialog: MatDialog) {}

  ngOnInit(): void {}

  select(): void {
    this.count = -1;
    this.selectionService.find(this.userUrl.value).subscribe((e) => {
      this.products = e;
    });
  }

  _c(row: unknown): ProductModel {
    return row as ProductModel;
  }

  selectProduct(productModel: ProductModel, isSelect: boolean): void {
    if (!isSelect) {
      this.selectProducts.push(productModel);
    } else {
      this.selectProducts.splice(this.selectProducts.indexOf(productModel));
    }
  }

  save(): void {
    this.selectionService.save(this.userUrl.value, this.selectProducts).subscribe();

    const dialogRef = this.dialog.open(SaveDialogComponent, {
      width: '250px'
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.count = 0;
      this.userUrl.setValue('');
    });
  }

  getErrorMessage(): string {
    if (this.userUrl.hasError('required')) {
      return 'Вы должны ввести значение';
    }

    return this.userUrl.hasError('userUrl') ? 'Не правильная ссылка на профиль' : '';
  }
}
