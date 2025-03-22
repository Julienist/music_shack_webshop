import { AbstractControl, FormGroup } from '@angular/forms';

export function isControlTouchedOrDirty(formGroup: FormGroup, controlName: string): boolean {
    const control = formGroup.get(controlName);
    return control ? (control.touched || control.dirty) : false;
}
  
export function hasControlError(formGroup: FormGroup, controlName: string, error: string): boolean {
    const control = formGroup.get(controlName);
    return control ? control.hasError(error) : false;
}
  
export function getControl(formGroup: FormGroup, controlName: string): AbstractControl | null {
    return formGroup.get(controlName);
}

export function digitValidator(control: AbstractControl): { [key: string]: boolean } {
  const password = control.value;
  const hasDigit = /\d/.test(password);
  return hasDigit ? {} : { 'digit': true };
}

export function lowercaseValidator(control: AbstractControl): { [key: string]: boolean } {
  const password = control.value;
  const hasLowercase = /[a-z]/.test(password);
  return hasLowercase ? {} : { 'lowercase': true };
}

export function uppercaseValidator(control: AbstractControl): { [key: string]: boolean } {
  const password = control.value;
  const hasUppercase = /[A-Z]/.test(password);
  return hasUppercase ? {} : { 'uppercase': true };
}

export function specialCharValidator(control: AbstractControl): { [key: string]: boolean } {
  const password = control.value;
  const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
  return hasSpecialChar ? {} : { 'specialChar': true };
}
