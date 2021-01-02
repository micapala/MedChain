import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { pipe } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';

  constructor(private authService: AuthService, private router: Router) {
    if (this.authService.userValue) {
      this.router.navigate(['/']);
    }
   }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(){
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    })
  }
  loginProcess(){
    if(this.loginForm.invalid){
      return;
    }

    if(this.loginForm.valid){
      this.authService.login(this.loginForm.value).subscribe(result => {
        if(result === true) {
          this.router.navigate(['/'])
        }
        else{
          this.error = "asdasdsadas";
        }
      })

    }
  }

}
