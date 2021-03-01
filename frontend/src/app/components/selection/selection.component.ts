import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { SelectionService } from '../../services/selection.service';
import { ProductModel } from '../../model/product.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog } from '@angular/material/dialog';
import { GroupModel } from '../../model/group.model';
import { UserVkModel } from '../../model/userVk.model';
import { CategoryModel } from '../../model/category.model';
import { SelfDialogComponent } from '../self-dialog/self-dialog.component';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.scss']
})
export class SelectionComponent implements OnInit {
  columns = ['id', 'name', 'description', 'price', 'catalogUrl', 'function'];
  columnsGroup = ['id', 'photo', 'name'];
  columnsCategory = ['name', 'topics'];
  products: ProductModel[] = [];
  groups: GroupModel[] = [];
  users: UserVkModel[] = [];
  selectionElement: GroupModel | undefined = undefined;
  categories: CategoryModel[] = [];
  topics: string[] = [];
  datasourceProduct = new MatTableDataSource<ProductModel>(this.products);
  initialSize = 0;
  userUrlGroup: FormGroup = new FormGroup({
    userUrlCtrl: new FormControl('', [
      Validators.required,
      Validators.pattern(/https?:\/\/(www\.)?vk\.com\//i)
    ])
  });
  selectProducts: ProductModel[] = [];
  selection = new SelectionModel<ProductModel>(true, []);

  constructor(
    private readonly selectionService: SelectionService,
    public dialog: MatDialog,
    private changeDetectorRefs: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.refresh();
  }

  select(): void {
    this.selectionService.findUser(this.userUrlGroup.controls.userUrlCtrl.value).subscribe((e) => {
      this.users[0] = e;
    });

    this.selectionService
      .findGroups(this.userUrlGroup.controls.userUrlCtrl.value)
      .subscribe((e) => {
        this.groups = e;
      });
  }

  selectProduct(productModel: ProductModel, isSelect: boolean): void {
    if (!isSelect) {
      this.selectProducts.push(productModel);
    } else {
      this.selectProducts.splice(this.selectProducts.indexOf(productModel));
    }
  }

  save(): void {
    this.selectionService.save('/', this.selectProducts).subscribe();
  }

  findCategories(): void {
    for (const group of this.groups) {
      for (const topic of group.topics) {
        this.topics[this.topics.length] = topic;
      }
    }

    this.selectionService.findCategories(this.topics).subscribe((e) => {
      this.categories = e;
    });
  }

  findProducts(): void {
    this.selectionService.findProducts(this.categories).subscribe((e) => {
      this.products = e;
      this.initialSize = this.products.length;
      this.refresh();
    });
  }

  selectRow(row: any): void {
    this.selectionElement = row as GroupModel;
  }

  generateUrlGroup(screenName: string): string {
    return 'https://vk.com/' + screenName;
  }

  _group(row: any): GroupModel {
    return row as GroupModel;
  }

  _category(row: any): CategoryModel {
    return row as CategoryModel;
  }

  _product(row: any): ProductModel {
    return row as ProductModel;
  }

  selectSelf(): void {
    const dialogRef = this.dialog.open(SelfDialogComponent, {
      width: '80%',
      height: '80%',
      data: this.products
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.products.push(result);
        this.refresh();
      }
    });
  }

  refresh(): void {
    this.datasourceProduct = new MatTableDataSource<ProductModel>(this.products);
    this.changeDetectorRefs.detectChanges();
  }
}
