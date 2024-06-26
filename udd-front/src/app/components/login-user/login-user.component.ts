import { Component, OnInit } from '@angular/core';
import { AuthRequest } from 'src/app/model/user.model';
import { AuthService } from 'src/app/service/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login-user',
  templateUrl: './login-user.component.html',
  styleUrls: ['./login-user.component.css']
})
export class LoginUserComponent implements OnInit {

  title = 'Login';
  request = new AuthRequest();
  submitted = false;
  message: string= "";

  constructor(
    private authService: AuthService

  ) { }

  ngOnInit(): void {
      
  }

  onSubmit(){

    if (this.request.email == '' || this.request.password == '') {
      this.message = 'Email or password missing.';
    } else {
      this.submitted = true;
      console.log(this.request)
      this.authService.login(this.request).subscribe(
        {
          next: (res) => {
            console.log(res)
            this.successfulLogin(res);
            Swal.fire({
              icon: 'success',
              title: 'Success!',
              text: 'Sucessfully logged in!',
            })
            window.location.href = '/';   
           
          },
          error: (e) => {
            this.submitted = false;
            console.log(e);
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Invalid email or password.',
            })   
          
          }
        });
    
    }
  }
  successfulLogin(data: any) {
    this.authService.setLoggedUser(data);
  }
}
