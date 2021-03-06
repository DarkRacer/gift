import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SelectionComponent } from './components/selection/selection.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import {HttpClientJsonpModule, HttpClientModule} from '@angular/common/http';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import {MatStepperModule} from '@angular/material/stepper';
import {MatCardModule} from '@angular/material/card';
import {MatExpansionModule} from '@angular/material/expansion';
import { SelfDialogComponent } from './components/self-dialog/self-dialog.component';
import {JsonpModule } from '@angular/http';
import { UserComponent } from './components/user/user.component';
import { LoginComponent } from './components/login/login.component';
import {MatListModule} from "@angular/material/list";
import {MatDividerModule} from "@angular/material/divider";
import {CoreModule} from "./core/core.module";
import {MatMenuModule} from "@angular/material/menu";
import {MatGridListModule} from "@angular/material/grid-list";
import { ProductsSelectionDialogComponent } from './components/products-selection-dialog/products-selection-dialog.component';
import { AdminComponent } from './components/admin/admin.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { ForbiddenComponent } from './components/exception/forbidden/forbidden.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { HomePageComponent } from './components/home-page/home-page.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SelectionErrorComponent } from './components/exception/selection-error/selection-error.component';

@NgModule({
  declarations: [AppComponent, SelectionComponent, SelfDialogComponent, UserComponent, LoginComponent, ProductsSelectionDialogComponent, AdminComponent, ForbiddenComponent, HomePageComponent, SelectionErrorComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    HttpClientJsonpModule,
    JsonpModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatToolbarModule,
    MatTableModule,
    MatButtonModule,
    ReactiveFormsModule,
    FormsModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatCheckboxModule,
    MatDialogModule,
    MatStepperModule,
    MatCardModule,
    MatExpansionModule,
    MatListModule,
    MatDividerModule,
    CoreModule,
    MatMenuModule,
    MatGridListModule,
    ScrollingModule,
    MatPaginatorModule,
    MatAutocompleteModule,
    MatSnackBarModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
