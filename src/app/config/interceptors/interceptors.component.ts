import { inject } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { HttpHandlerFn, HttpRequest } from '@angular/common/http';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const loginService = inject(LoginService)
  const authToken = loginService.getToken();
  console.log("interceptor: authtoken: " + authToken)
  if (authToken != null){
    const newRequest = req.clone({
      headers: req.headers.set('Authorization',`Bearer ${authToken}`),
    });
    console.log("New request")
    return next(newRequest);
  }

  return next(req);
}
