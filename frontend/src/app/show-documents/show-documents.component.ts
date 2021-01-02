import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DocumentsService } from '../service/documents.service';
import {HealthDocument} from '../models/document';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-show-documents',
  templateUrl: './show-documents.component.html',
  styleUrls: ['./show-documents.component.css']
})
export class ShowDocumentsComponent implements OnInit {

  documents: HealthDocument[];
  docObservable: Observable<HealthDocument[]>;
  dataSource;
  displayedColumns = [];
  @ViewChild(MatSort) sort: MatSort;


  constructor(private documentsService: DocumentsService) { }

  ngOnInit(): void {
    this.docObservable = this.documentsService.getAllDocuments();

    this.displayedColumns =["docType", "preparationDate", "download","actions"];
    this.createTable();
  }

  createTable() {
    this.dataSource = new MatTableDataSource(this.documents);
  }

  downloadDocument(fileNum: string){
    this.documentsService.downloadDocument(fileNum).subscribe(response => {
			let blob:any = new Blob([response], { type: 'text/json; charset=utf-8' });
			const url = window.URL.createObjectURL(blob);
			//window.open(url);
			//window.location.href = response.url;
			fileSaver.saveAs(blob, 'plik.json');
		}), error => console.log('Error downloading the file'),
                 () => console.info('File downloaded successfully');

  }

}
