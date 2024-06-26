import { Component } from '@angular/core';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(private authService: AuthService) { }
  isLoggedIn: boolean = false;

  ngOnInit(): void {
    this.refreshUser();
  }

  logout() {
    this.authService.logout();
  }

  refreshUser(): void {
    let user = localStorage.getItem('currentUser');
    let role = localStorage.getItem('role');
    this.authService.getCurrentUser()
    if(user === null ){
      this.isLoggedIn = false;
    } else{
      this.isLoggedIn = true;
    }
  }

}
