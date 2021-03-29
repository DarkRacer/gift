import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SelectionComponent } from './components/selection/selection.component';
import {UserComponent} from "./components/user/user.component";
import {LoginComponent} from "./components/login/login.component";

const routes: Routes = [
  {
    path: 'selection',
    component: SelectionComponent,
  },
  {
    path: 'user',
    component: UserComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
