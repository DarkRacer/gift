import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-selection-error',
  templateUrl: './selection-error.component.html',
  styleUrls: ['./selection-error.component.scss']
})
export class SelectionErrorComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  reload() {
    window.location.reload();
  }
}
