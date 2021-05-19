import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SelectionComponent } from './components/selection/selection.component';
import {UserComponent} from "./components/user/user.component";
import {LoginComponent} from "./components/login/login.component";
import {AuthGuardGuard} from "./core/guard/auth-guard.guard";
import { AdminComponent } from './components/admin/admin.component';
import { UserRole } from './model/user-role.model';
import { ForbiddenComponent } from './components/exception/forbidden/forbidden.component';
import { HomePageComponent } from './components/home-page/home-page.component';

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
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuardGuard],
    data: {roles: [UserRole.ADMIN]}
  },
  {
    path: 'forbidden',
    component: ForbiddenComponent
  },
  {
    path: '',
    component: HomePageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
