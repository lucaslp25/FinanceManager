import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { App } from './pages/app/app';
import { authGuard } from './core/guards/auth-guard';
import { loginGuardGuard } from './core/guards/login-guard-guard';
import { Register } from './pages/register/register';
import { Wallet } from './core/components/wallet/wallet';
import { MyExpenses } from './core/components/my-expenses/my-expenses';

export const routes: Routes = [
    // login is the default route 
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login', component: Login, title: 'Finance Manager - Login', canActivate: [loginGuardGuard]},
    {path: 'register', component: Register, title: 'Finance Manager - Register', canActivate: [loginGuardGuard]},
    
    {path: 'app', component: App, title: 'Finance Manager - Home', canActivate: [authGuard],
        children: [

            // default path of home -- wallet
            {path: '', redirectTo: 'wallet', pathMatch: 'full'},
            {path: 'wallet', component: Wallet, title: 'Minha Carteira'},


            {path: 'my-expenses', component: MyExpenses, title: 'Meus Gastos'}
        ]
    },
    
    //for any unknowledge route, send to login page.
    {path: '**', redirectTo: 'login'}
];
