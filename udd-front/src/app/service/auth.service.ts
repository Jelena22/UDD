import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthRequest, User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiHost: string = 'http://localhost:8080/api/auth/';
  url = this.apiHost + 'login';
  activate_account_url = this.apiHost + 'auth/activate-account/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  private user = new User();
  jwt: string = "";
  expiresIn: number = -1;

  constructor(private http: HttpClient) {}

  login(request: AuthRequest) {
    return this.http.post<any>(this.url, request);
  }

  setLoggedUser(data: any) {
    this.user = data.user;
    console.log(this.user)
    this.jwt = data.accessToken;
    console.log(this.jwt)
    this.expiresIn = data.expiresIn;
    console.log(this.expiresIn)
    localStorage.setItem('jwt', this.jwt);
    localStorage.setItem('refreshToken', JSON.stringify(this.expiresIn));
    localStorage.setItem('currentUser', JSON.stringify(this.user));
    localStorage.setItem('role', data.user.role.name);
    console.log(localStorage.getItem('role'));
    console.log(localStorage.getItem('currentUser'));

  }
  getCurrentUser(): User {
    return JSON.parse(localStorage.getItem('currentUser')!);
  }

  logout() { 
    this.jwt = "";
    localStorage.clear();
    window.location.href = '/login';
  }
}
