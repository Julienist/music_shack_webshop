import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { digitValidator, getControl, hasControlError, isControlTouchedOrDirty, lowercaseValidator, specialCharValidator, uppercaseValidator } from '../config/account-validaton.component';
import { NgIf } from '@angular/common';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ FormsModule, NgIf, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  protected loginService = inject(LoginService);
  private router = inject(Router);

  protected loginForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      "email": ["", [
        Validators.required,
        Validators.email
      ]],
      "password": ["", [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(30),
        digitValidator,
        lowercaseValidator,
        uppercaseValidator,
        specialCharValidator
      ]]
    });
  }

  isPasswordTouchedOrDirty(): boolean {
    return isControlTouchedOrDirty(this.loginForm, 'password');
  }

  hasPasswordError(error: string): boolean {
    return hasControlError(this.loginForm, 'password', error);
  }

  get email() {
    return getControl(this.loginForm, 'email');
  }

  onLogin() {
    if (this.loginForm.valid) {
      console.log("Result: email:"+ this.loginForm.value.email + " password: "+ this.loginForm.value.password);
      const loginData = { email: this.loginForm.value.email, password: this.loginForm.value.password };
      this.loginService.authenticate('login', loginData).subscribe({
        next: (responseData) => {
          console.log(responseData);
          this.router.navigate(['/producten/AlleProducten']);
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }

  navigateToMaakAccount() {
    this.router.navigate(['register']);
  }
}
