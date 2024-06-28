import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  apiHost: string = 'http://localhost:8080/api/index/';
  url = this.apiHost + 'law';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });


  constructor(private http: HttpClient) { }

  uploadFiles(files: File[]): Observable<any> {
    const formData: FormData = new FormData();
    files.forEach(file => {
      formData.append('files', file, file.name);
    });

    //const headers = new HttpHeaders();
    //headers.append('Content-Type', 'multipart/form-data');

    return this.http.post(this.url, formData);
  }
}
