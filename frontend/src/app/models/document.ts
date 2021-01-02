export class HealthDocument {
  documentID: string;
  preparationDate: string;
  docType: string;

  constructor(document?: any){
    if(document){
      this.documentID = document.documentID;
      this.preparationDate = document.preparationDate;
      this.docType = document.docType;
    }
  }
}
