import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { AuthGuard } from './auth/guard/auth.guard';
import { AppGuard } from './guards/app.guard';
import { HomeComponent } from './home/home.component';
import { Role } from './models/role';
import { ShowDocumentsComponent } from './show-documents/show-documents.component';
import { RegisterComponent } from './register/register.component';
import { AddDocumentComponent } from './add-document/add-document.component';


const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [AppGuard]
},
{
    path: 'login',
    component: LoginComponent
},
{
  path: 'documentsList',
  component: ShowDocumentsComponent,
  canActivate: [AppGuard],
  data: {roles: [Role.Lekarz, Role.Pacjent]}
},
{
  path: 'register',
  component: RegisterComponent,
  canActivate: [AppGuard],
  data: {roles: [Role.Administrator]}
},
{
  path: 'addDocument',
  component: AddDocumentComponent,
  canActivate: [AppGuard]
},

// otherwise redirect to home
{ path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
