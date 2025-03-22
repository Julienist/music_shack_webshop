import { Routes } from '@angular/router';
import { ProductPageComponent } from './product-page/product-page.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

// const canAccessWinkelmandje: canMatchFn = (route, segments) => {
    // const router = inject(Router)
    // const loginService = inject(LoginService)
    // if(loginService).isLoggedIn()){
        // return true;
    // }
    // return new RedirectCommand(router.parseUrl("/"))
// }

export const routes: Routes = [
    {
        path: 'products',
        component: ProductPageComponent

    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: '',
        redirectTo: 'products',
        pathMatch: 'full'
    },
    {
        path: '**',
        component: PageNotFoundComponent
    },
];
