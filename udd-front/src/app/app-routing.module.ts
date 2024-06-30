import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { LoginUserComponent } from './components/login-user/login-user.component';
import { SearchLawComponent } from './components/search-law/search-law.component';
import { SearchContractComponent } from './components/search-contract/search-contract.component';
import { SearchContractGeolocationComponent } from './components/search-contract-geolocation/search-contract-geolocation.component';

const routes: Routes = [
  {
    path: 'register-user',
    component: RegisterUserComponent
  },
  {
    path: 'login',
    component: LoginUserComponent
  },
  {
    path: 'search-laws',
    component: SearchLawComponent
  },
  {
    path: 'search-contracts',
    component: SearchContractComponent
  },
  {
    path: 'search-contract-geolocation',
    component: SearchContractGeolocationComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
