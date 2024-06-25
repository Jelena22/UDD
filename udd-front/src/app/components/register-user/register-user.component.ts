import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user.model';
import { RegisterUserService } from 'src/app/service/register-user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit{

  title = 'User Registration';
  user = new User();
  passwordRepeated: string= "";
  message: string="";
  submitted = false;

  constructor(
    private router: Router,
    private registerUserService: RegisterUserService
  ) { }

  ngOnInit(): void {
   
  }

  onSubmit(){
    if (
      !this.validateFirstName() ||
      !this.validateLastName() ||
      !this.validateEmail() ||
      !this.validatePassword()
    ) {
      this.submitted = false;
      return
    }

    this.registerUserService.registerUser(this.user).subscribe(
      {
        next: (res) => {
          this.router.navigate(['/login']);
          Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: 'Successful registration!',
          })   
         
        },
        error: (e) => {
          this.submitted = false;
          console.log(e);
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Email already exists.',
          })   
        
        }

    });
  }

  validateFirstName() {
    if (this.user.name.length < 2) {
      this.message = "Your first name should contain at least 2 characters!";
      return false;
    } else if (this.user.name.match(/[!@#$%^&*.,:'<>+-/\\"]/g)) {
      this.message = "Your first name shouldn't contain special characters.";
      return false;
    } else if (this.user.name.match(/\d/g)) {
      this.message = "Your first name shouldn't contain numbers!";
      return false;
    } else if (!/^[A-Z][a-z]+$/.test(this.user.name)) {
      this.message = "Your first name needs to have one upper letter at the start!";
      return false;
    }
    return true;
  }

  validateLastName() {
    if (this.user.surname.length < 2) {
      this.message = "Your last name should contain at least 2 characters!";
      return false;
    } else if (this.user.surname.length > 36) {
      this.message = "Your last name shouldn't contain more than 36 characters!";
      return false;
    } else if (this.user.surname.match(/[!@#$%^&*.,:'<>+-/\\"]/g)) {
      this.message = "Your last name shouldn't contain special characters.";
      return false;
    } else if (this.user.surname.match(/\d/g)) {
      this.message = "Your last name shouldn't contain numbers!";
      return false;
    } else if (!/^[A-Z][a-z]+[ ]?[A-Z]*[a-z]*$/.test(this.user.surname)) {
      this.message = "Your last name needs to have one upper letter at the start!";
      return false;
    }
    return true;
  }

  validateEmail() {
    if (this.user.email == "") {
      this.message = "Please enter your email.";
      return false;
    } else if (
      !/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(this.user.email)
    ) {
      this.message = "You have entered an invalid email!";
      return false;
    }
    return true;
  }

  validatePassword() {
    if (this.user.password.length < 6) {
      this.message = "Your password should contain at least 6 character!";
      return false; 
    } else if (!this.user.password.match(/[A-Z]/g)) {
      this.message = "Your password should contain at least one big letter.";
      return false;
    } else if (!this.user.password.match(/[0-9]/g)) {
      this.message = "Your password should contain at least one number.";
      return false;
    } else if (this.user.password !== this.passwordRepeated) {
      this.message = "Passwords do not match!";
      return false;
    }
    return true;
  }


}
