import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {
  typesOfOptions: string[] = ['Управление ролями', 'Управление словами', 'Управление предложениями'];

  constructor() { }

  ngOnInit(): void {
  }

}
