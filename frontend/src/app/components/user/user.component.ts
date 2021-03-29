import { Component, OnInit } from '@angular/core';
import { UserVkModel } from '../../model/userVk.model';
import { SelectionService } from '../../services/selection.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  users: UserVkModel[] = [];
  typesOfShoes: string[] = ['Редактировать', 'Wish лист', 'Подобранные подарки'];

  constructor(private readonly selectionService: SelectionService, private router: Router) {}

  ngOnInit(): void {
    this.selectionService.findUser('https://vk.com/id146812045').subscribe((e) => {
      this.users[0] = e;
    });
  }

  menu(select: string): void {
    if (select === this.typesOfShoes[0]) {
      console.log('red');
    } else if (select === this.typesOfShoes[1]) {
      this.router.navigate(['wish']);
    } else {
      console.log('history');
    }
  }
}
