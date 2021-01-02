import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/services/auth.service';
import { Role } from '../models/role';
import { User } from '../models/user';
import {HealthDocument} from '../models/document';
import * as fileSaver from 'file-saver';

@Injectable({
  providedIn: 'root'
})
export class DocumentsService {

  user : User;

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {
    this.user = this.authService.userValue;
   }


  getAllDocuments(): Observable<HealthDocument[]>{

    var url;
    if(this.user.role === Role.Lekarz){
      url = `api/documents/getAllDoctorDocuments/${this.user.login}`
    }
    else if(this.user.role === Role.Pacjent){
      url = `api/documents/getAllPatientDocuments/${this.user.login}`
    }
    console.log(url);
    return this.http.get<HealthDocument[]>(url);

  }

  downloadDocument(docFile: string): any{
    return this.http.get(`api/documents/getDocument/${docFile}`, {responseType: 'blob'});

  }

}
