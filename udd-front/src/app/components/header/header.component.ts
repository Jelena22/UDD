import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  dashboardLoaded = false;
  isLoggedIn: boolean = false;
  isAdmin: boolean = false;
  isRegularUser: boolean = false;
  @ViewChild('kibanaIframe') kibanaIframe!: ElementRef;

  constructor(private authService: AuthService, private router: Router) { }

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
    if(role == "ROLE_ADMIN") {
      this.isAdmin = true;
    }
    if(role == "ROLE_REGULAR_USER"){
      this.isRegularUser = true;
    }
  }

  loadDashboard(): void {
    this.dashboardLoaded = true; // Postavlja se da je dashboard učitan

    const kibanaDashboardUrl = 'http://localhost:5601/app/dashboards#/view/d51e9d43-5952-440d-bb92-b7f084c18d79?_g=(filters:!(),refreshInterval:(pause:!t,value:60000),time:(from:now%2Fd,to:now%2Fd))';
    
    setTimeout(() => { // Možda je potrebno malo vremena pre nego što se iframe element inicijalizuje
      if (this.kibanaIframe) {
        const iframeElement = this.kibanaIframe.nativeElement as HTMLIFrameElement;
        iframeElement.src = kibanaDashboardUrl;
      } else {
        console.error('iFrame element not found.');
      }
    }, 100);
  }

  refreshPage(): void {
    window.location.reload();
  }

}
