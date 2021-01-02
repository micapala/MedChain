import { Injectable } from '@angular/core';
import { CanActivate, Router, CanLoad, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../auth/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AppGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    const user = this.authService.userValue;
    if(user){
      if (route.data.roles && route.data.roles.indexOf(user.role) === -1){
        this.router.navigate(['/']);
        return false;
      }
      return true;
    }
    this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
    return false;
  }

  // canLoad(){
  //   if (!this.authService.isLoggedIn()){
  //     this.router.navigate(['/login']);
  //   }
  //   return this.authService.isLoggedIn();
  // }



}
