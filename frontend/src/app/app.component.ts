import {ChangeDetectorRef, Component} from '@angular/core';
import {SelectionService} from "./services/selection.service";
import {MatDialog} from "@angular/material/dialog";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'Gift';

  constructor(
    private readonly selectionService: SelectionService
  ) {}

  login() {
    this.selectionService.loginVk().subscribe((e) => console.log(e));
  }

  sout(a :any) {
    console.log(a)
  }
}
