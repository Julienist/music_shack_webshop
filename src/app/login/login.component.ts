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
import {MatDividerModule} from '@angular/material/divider';
import {
  MatFormFieldModule,
  MatLabel
} from '@angular/material/form-field';
import {
  digitValidator,
  getControl,
  hasControlError as importedHasControlError,
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
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatLabel,
    MatButtonModule,
    MatDividerModule,
    TranslatePipe,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  protected loginService = inject(LoginService);
  private router = inject(Router);
  private translate = inject(TranslateService);
  private fb = inject(FormBuilder);

  protected loginForm: FormGroup;

  ngOnInit() {
    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
  }

  constructor() {
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

  // Expose the imported function as a method
  hasControlError(formGroup: FormGroup, controlName: string, error: string): boolean {
    return importedHasControlError(formGroup, controlName, error);
  }

  isPasswordTouchedOrDirty(): boolean {
    return isControlTouchedOrDirty(this.loginForm, 'password');
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
