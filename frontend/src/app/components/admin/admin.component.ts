import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { CategoryWordsModel } from '../../model/category-words.model';
import { CategoryModel } from '../../model/category.model';
import { MatListOption } from '@angular/material/list';
import { UserAndRoleModel } from '../../model/user-and-role.model';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { UserRole } from '../../model/user-role.model';

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
  users: UserAndRoleModel[] = [];
  columns = ['id', 'picture', 'name', 'function'];
  userAndRoleModelSelectionModel = new SelectionModel<UserAndRoleModel>(true, []);

  constructor(private readonly adminService: AdminService) { }

  ngOnInit(): void {
    this.selectedCategories = [];
    this.categoryWords = [];
    this.categories = [];

    this.adminService.findAllCategoryWords().subscribe(e => {
      this.categoryWords = e;
    })

    this.adminService.findAllCategory().subscribe(e => {
      this.categories = e;
    })

    this.adminService.findAllUser().subscribe(e => {
      console.log(e);
      this.users = e;
      console.log(this.users);
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

    });
  }

  _user(row: any): UserAndRoleModel {
    return row as UserAndRoleModel;
  }

  selectUser(userAndRoleModel: UserAndRoleModel, isSelect: boolean) {
    if (userAndRoleModel.role.indexOf(UserRole.ADMIN) == -1) {
      this.adminService.saveAdmin(userAndRoleModel).subscribe(e => {
        window.location.reload();
      });
    } else {
      this.adminService.deleteAdmin(userAndRoleModel).subscribe(e => {
        window.location.reload();
      });
    }

  }

  admin(row : UserAndRoleModel) {
    return row.role.indexOf(UserRole.ADMIN) != -1;
  }
}
