import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { catchError, mapTo, tap } from 'rxjs/operators';
import { BehaviorSubject, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient){}

  getUserData(login: string): Observable<User>{
    return this.http.get<User>(`api/user/userData/${login}`);
  }
  registerUser(register: {login: string, password: string, role: string, name: string, surname: string, gender: string, attrs: string}): Observable<boolean>{

    return this.http.post<any>(`api/user/registerUser`, register)
      .pipe(
        tap(token =>
        mapTo(true),
        catchError(error => {
          alert(error.error);
          return of(false);
        })));

  }
}
