
import { LoginComponent } from './auth/login/login.component';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from '@angular/material/toolbar';
import {BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { HomeComponent } from './home/home.component';
import { ShowDocumentsComponent } from './show-documents/show-documents.component';
import {MatTableModule} from '@angular/material/table';
import { RegisterComponent } from './register/register.component';
import {MatSelectModule} from '@angular/material/select';
import { AddDocumentComponent } from './add-document/add-document.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ShowDocumentsComponent,
    RegisterComponent,
    AddDocumentComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatToolbarModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatSelectModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },],
  bootstrap: [AppComponent]
})
export class AppModule { }
