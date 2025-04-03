import { Component, signal } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import { merge } from 'rxjs';

@Component({
  selector: 'app-paymentpage',
  imports: [MatButtonModule, MatDividerModule, MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule],
  templateUrl: './paymentpage.component.html',
  styleUrl: './paymentpage.component.scss'
})
export class PaymentpageComponent {
  readonly pincode = new FormControl('', [Validators.required, Validators.minLength(4)]);

  errorMessage = signal('');

  constructor() {
    merge(this.pincode.statusChanges, this.pincode.valueChanges)
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateErrorMessage());
  }

  updateErrorMessage() {
    if (this.pincode.hasError('required')) {
      this.errorMessage.set("Je moet minimaal 4 cijfers invullen. De pincode word NIET opgeslagen");
    } else if (this.pincode.hasError('pincode')) {
      this.errorMessage.set('Not a valid pin');
    } else {
      this.errorMessage.set('');
    }
  }

  onPay() {
    
  }
}

