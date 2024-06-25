import { Component, OnInit } from '@angular/core';
import { AuthRequest } from 'src/app/model/user.model';

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

  ngOnInit(): void {
      
  }

  onSubmit(){}
}
