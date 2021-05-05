import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { CategoryWordsModel } from '../../model/category-words.model';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AdminComponent implements OnInit {
  typesOfOptions: string[] = ['Управление ролями', 'Управление словами', 'Управление предложениями'];
  selectionOption: string = "";
  categoryWords: CategoryWordsModel[] = [];
  selectedWordCategories: CategoryWordsModel | undefined;

  constructor(private readonly adminService: AdminService) { }

  ngOnInit(): void {
      this.adminService.findAllCategoryWords().subscribe(e => {
        this.categoryWords = e;
      })
  }

}
