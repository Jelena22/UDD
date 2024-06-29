import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


export interface SearchQueryDTO {
  keywords: string[];
  
  
}

export interface LawIndexResultsDTO {
  downloadUrl: string;
  serverFileName: string;
  title: string;
  highlight: string[];
}

export interface SearchResult {
  content: LawIndexResultsDTO[];
  pageable: any;
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  sort: any[];
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  apiHost: string = 'http://localhost:8080/api/search/';
  url = this.apiHost + 'law';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  search(query: SearchQueryDTO): Observable<SearchResult> {
    return this.http.post<SearchResult>(`${this.url}`, query);
  }
}
