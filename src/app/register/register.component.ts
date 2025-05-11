import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import {
  MatFormFieldModule,
  MatLabel
} from '@angular/material/form-field';
import {
  digitValidator,
  getControl,
  hasControlError,
  isControlTouchedOrDirty,
  lowercaseValidator,
  specialCharValidator,
  uppercaseValidator
} from '../config/account-validaton.component';
import { NgIf } from '@angular/common';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { TranslateService, TranslatePipe } from '@ngx-translate/core';


@Component({
  selector: 'app-maak-account',
  standalone: true,
  imports: [
    NgIf,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatLabel,
    MatButtonModule,
    TranslatePipe
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  protected loginService = inject(LoginService);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  protected authForm: FormGroup;

  constructor() {
    this.authForm = this.fb.group({
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

  hasControlError(formGroup: FormGroup, controlName: string, error: string): boolean {
    return hasControlError(formGroup, controlName, error);
  }

  isPasswordTouchedOrDirty(): boolean {
    return isControlTouchedOrDirty(this.authForm, 'password');
  }

  hasPasswordError(error: string): boolean {
    return hasControlError(this.authForm, 'password', error);
  }

  get email() {
    return getControl(this.authForm, 'email');
  }

  onMakeAccount() {
    if (this.authForm.valid) {
      console.log("Result: email:"+ this.authForm.value.email + " password: "+ this.authForm.value.password);
      const accountData = { email: this.authForm.value.email, password: this.authForm.value.password };
      this.loginService.authenticate('register', accountData).subscribe({
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

}
