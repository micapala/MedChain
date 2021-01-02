import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError, mapTo, tap } from 'rxjs/operators';
import { baseUrl } from 'src/environments/environment';
import { Token } from 'src/app/token';
import { User } from '../../models/user';
import { UseExistingWebDriver } from 'protractor/built/driverProviders';
import { Router } from '@angular/router';




@Injectable({
  providedIn: 'root'
})

export class AuthService {

  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>;

  constructor(private http: HttpClient, private router: Router) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')));
    this.user = this.userSubject.asObservable();
  }


  login(user: {username: string, password: string}): Observable<boolean> {
    return this.http.post<any>(`api/authenticate`, user)
      .pipe(
        tap(token => this.doLoginUser(user.username, token)),
        mapTo(true),
        catchError(error => {
          alert(error.error);
          return of(false);
        }));
  }

  public get userValue(): User {
    return this.userSubject.value;
  }

  isLoggedIn() {
    return !!this.getJwtToken();
  }

  getJwtToken() {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  logout() {
    localStorage.removeItem(this.JWT_TOKEN);
    localStorage.removeItem('user');
    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }

  private doLoginUser(username: string, token: Token) {
    const encodedPayload = token.jwt.split('.')[1];
    const payload = window.atob(encodedPayload);
    const decodedJwtData = JSON.parse(payload);

    var tmp_user = new User();
    tmp_user.login = username;
    tmp_user.role = decodedJwtData.roles[0]['authority'];
    console.log(tmp_user);
    localStorage.setItem('user', JSON.stringify(tmp_user));
    console.log(tmp_user.login);
    this.userSubject.next(tmp_user);

    localStorage.setItem(this.JWT_TOKEN, token.jwt);
  }





}
