import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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

export interface SearchQueryContractByNameAndSurnameDTO {
  name: string;
  surname: string;
}

export interface ContractSearchResult {
  name: string;
  surname: string;
  governmentName: string;
  address: string;
  administrationLevel: string;
  downloadUrl: string;
  serverFileName: string;
  title: string;
  highlight: string[];
}

export interface SearchResult1 {
  content: ContractSearchResult[];
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

export interface SearchResultGovernment {
  content: ContractSearchResult[];
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

export interface SearchQueryContractByGovernmentNameAnsLevelDTO {
  governmentName: string;
  administrationLevel: string;
}

export interface SearchByGeolocationDTO {
  city: string;
  distanceInKm: number;
}

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  apiHost: string = 'http://localhost:8080/api/search/';
  url = this.apiHost + 'law';
  url1 = this.apiHost + 'contract/by-content';
  url2 = this.apiHost + 'contract/by-name-and-surname';
  url3 = this.apiHost + 'contract/by-government-name-and-level';
  url4 = this.apiHost + 'contract/by-geolication';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  search(query: SearchQueryDTO, page: number, size: number): Observable<SearchResult> {
    const params = new HttpParams()
    .set('page', page.toString())
    .set('size', size.toString());
    return this.http.post<SearchResult>(`${this.url}`, query, { params });
  }

  searchContractByContent(query: SearchQueryDTO, page: number, size: number): Observable<SearchResult> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.post<SearchResult>(`${this.url1}`, query, { params });
  }

  searchContractByNameSurname(query: SearchQueryContractByNameAndSurnameDTO, page: number, size: number): Observable<SearchResult1> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.post<SearchResult1>(`${this.url2}`, query, { params });
  }

  searchContractByNameLevel(query: SearchQueryContractByGovernmentNameAnsLevelDTO, page: number, size: number): Observable<SearchResultGovernment> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.post<SearchResultGovernment>(`${this.url3}`, query, { params });
  }

  searchContractByGeolocation(query: SearchByGeolocationDTO, page: number, size: number): Observable<SearchResultGovernment> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.post<SearchResultGovernment>(`${this.url4}`, query, { params });
  }
}
