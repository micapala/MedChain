import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.initForm();
  }
  initForm(){
    this.registerForm = new FormGroup({
      login: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      role: new FormControl('', [Validators.required]),
      name: new FormControl('', [Validators.required]),
      surname: new FormControl('', [Validators.required]),
      gender: new FormControl('', [Validators.required]),
    })
  }
  onSubmit(){
    console.log('tutaj');
    if(this.registerForm.invalid){
      return;
    }

    if(this.registerForm.valid){
      this.userService.registerUser(this.registerForm.value).subscribe(result => {
        if(result === true) {
          this.router.navigate(['/'])
        }
        else{
          alert('Rejestracja nieudana');
        }
      })

    }

  }

}
