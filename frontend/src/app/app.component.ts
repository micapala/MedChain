import { Component } from '@angular/core';
import { AuthService } from './auth/services/auth.service';
import { Role } from './models/role';
import { User } from './models/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  user: User;
  title = 'frontend';

  constructor(private authService: AuthService){
    this.authService.user.subscribe(x => this.user = x);
  }

  get isPatient(){
    return this.user && this.user.role === Role.Pacjent;
  }

  get isLekarz(){
    return this.user && this.user.role === Role.Lekarz;
  }

  logout(){
    this.authService.logout();
  }
}
