import { AbstractControl, FormGroup } from '@angular/forms';

export function isControlTouchedOrDirty(formGroup: FormGroup, controlName: string): boolean {
    const control = formGroup.get(controlName);
    return control ? (control.touched || control.dirty) : false;
}
  
export function hasControlError(formGroup: FormGroup, controlName: string, error: string): boolean {
  // console.log('FormGroup:', formGroup);
  // console.log('ControlName:', controlName);
  // console.log('Error:', error);
  const control = formGroup.get(controlName);
  if (!control) {
    console.warn(`Control '${controlName}' does not exist in the form group.`);
    return false;
  }
  return control ? control.hasError(error) : false;
}
  
export function getControl(formGroup: FormGroup, controlName: string): AbstractControl | null {
  const control = formGroup.get(controlName);
  if (!control) {
    console.warn(`Control '${controlName}' does not exist.`);
  }
  return control;
}

export function digitValidator(control: AbstractControl): { [key: string]: boolean } {
  const password = control.value || ''; // Zorg ervoor dat het een string is.
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
