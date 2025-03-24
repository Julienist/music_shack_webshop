import { RouterModule, Routes } from '@angular/router';
import { ProductPageComponent } from './product-page/product-page.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RegisterComponent } from './register/register.component';
import { ProductsContainerComponent } from './product-page/products-container/products-container.component';
import { SpecificProductComponent } from './product-page/specific-product/specific-product.component';
import { NgModule } from '@angular/core';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';

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
        path: 'producten',
        component: ProductPageComponent,
        children: [
            {
                path: '',
                redirectTo: 'AlleProducten',
                pathMatch: 'full'
            },
            {
                path: 'AlleProducten',
                component: ProductsContainerComponent
            },
            {
                path: ':id',
                component: SpecificProductComponent
            }
        ],

    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'register',
        component: RegisterComponent
    },
    {
        path: 'winkelwagen',
        component: ShoppingCartComponent
    },
    {
        path: '',
        redirectTo: 'producten/AlleProducten',
        pathMatch: 'full'
    },
    {
        path: '**',
        component: PageNotFoundComponent
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }
