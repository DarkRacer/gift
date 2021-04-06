import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SelectionComponent } from './components/selection/selection.component';
import {UserComponent} from "./components/user/user.component";
import {LoginComponent} from "./components/login/login.component";
import {AuthGuardGuard} from "./core/guard/auth-guard.guard";

const routes: Routes = [
  {
    path: 'selection',
    component: SelectionComponent,
    canActivate: [AuthGuardGuard]
  },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [AuthGuardGuard]
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
