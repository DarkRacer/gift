import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SelectionService } from '../../services/selection.service';
import { ProductModel } from '../../model/product.model';
import { CategoryModel } from '../../model/category.model';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { map, startWith} from 'rxjs/operators';
import { Observable } from 'rxjs';
import { MatOptionSelectionChange } from '@angular/material/core';
@Component({
  selector: 'app-self-dialog',
  templateUrl: './self-dialog.component.html',
  styleUrls: ['./self-dialog.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SelfDialogComponent implements OnInit {
  categories: CategoryModel[] = [];
  categoryCtrl = new FormControl();
  columns = ['name', 'price', 'function'];
  categoriesSelection: string[] = [];
  // @ts-ignore
  filteredCategories: Observable<CategoryModel[]>;
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

    this.updateFilteredCategories();
  }

  _product(row: any): ProductModel {
    return row as ProductModel;
  }

  findProduct() {
    this.selectionService.findProduct(this.categoriesSelection).subscribe(e => {
      this.products = e;
      this.dataSource.data = this.products;
      this.cdr.detectChanges();
      this.resultsLength = this.products.length;
      this.dataSource.paginator = this.paginator;
    })
  }

  private _filter(value: string): CategoryModel[] {
    const filterValue = value.toLowerCase();

    return this.categories.filter(category => category.name.toLowerCase().includes(filterValue));
  }

  updateSelection($event: MatOptionSelectionChange) {
    this.categoriesSelection.push($event.source.value);

    this.categoryCtrl.setValue('');
    for (let category of this.categories) {
      if (category.name == $event.source.value) {
        this.categories.splice(this.categories.indexOf(category),1);
      }
    }
    this.updateFilteredCategories();
  }

  updateFilteredCategories () {
    this.filteredCategories = this.categoryCtrl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }
}
