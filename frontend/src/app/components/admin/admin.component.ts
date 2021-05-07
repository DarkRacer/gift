import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { CategoryWordsModel } from '../../model/category-words.model';
import { CategoryModel } from '../../model/category.model';
import { MatListOption } from '@angular/material/list';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AdminComponent implements OnInit {
  typesOfOptions: string[] = ['Управление ролями', 'Управление ключевыми словами', 'Управление предложениями'];
  selectionOption: string = "";
  categoryWords: CategoryWordsModel[] = [];
  categories: CategoryModel[] = [];
  selectedWordCategories: CategoryWordsModel | undefined;
  selectedCategories: CategoryModel[] = [];
  categoriesForSelection: CategoryModel[] = [];

  constructor(private readonly adminService: AdminService) { }

  ngOnInit(): void {
    this.selectedCategories = [];
    this.categoryWords = [];
    this.categories = [];

    this.adminService.findAllCategoryWords().subscribe(e => {
      this.categoryWords = e;
    })

    this.adminService.findAllCategory().subscribe(e => {
      console.log(e);
      this.categories = e;
    })
  }

  select(wordCategories: CategoryWordsModel) {
    this.categoriesForSelection = [];
    this.selectedWordCategories = wordCategories;
    this.selectedCategories = wordCategories.categories;

    for (let category of this.categories) {
        this.categoriesForSelection.push(category);
      }

    for (let category of wordCategories.categories) {
      this.categoriesForSelection.splice(this.categoriesForSelection.indexOf(category), 1);
    }
  }

  add(selected: MatListOption[]) {
    this.selectedCategories.push(selected[0].value);
    this.categoriesForSelection.splice(this.categoriesForSelection.indexOf(selected[0].value), 1);
  }

  delete(selected: MatListOption[]) {
    this.selectedCategories.splice(this.selectedCategories.indexOf(selected[0].value), 1);
    this.categoriesForSelection.push(selected[0].value);
  }

  save() {
    // @ts-ignore
    this.selectedWordCategories?.categories = this.selectedCategories;

    this.adminService.save(this.selectedWordCategories).subscribe(e => {
      window.location.reload();
    })
  }
}
