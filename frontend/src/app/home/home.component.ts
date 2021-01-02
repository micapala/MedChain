import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/services/auth.service';
import { User } from '../models/user';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  loading = false;
  user: User;
  userFromApi: User;


  constructor(private authService: AuthService, private userService: UserService) {
    this.user = this.authService.userValue;
   }

  ngOnInit(): void {
    this.user = this.authService.userValue;
    this.userService.getUserData(this.user.login).subscribe(
      user => {
        this.userFromApi = user;
        console.log(this.userFromApi);
      }
    )

  }

}
