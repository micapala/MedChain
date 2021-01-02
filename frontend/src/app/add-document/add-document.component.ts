import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DocumentsService } from '../service/documents.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.css']
})
export class AddDocumentComponent implements OnInit {

  registerForm: FormGroup;

  constructor(private documentService: DocumentsService, private router: Router) { }

  ngOnInit(): void {
  }

}
