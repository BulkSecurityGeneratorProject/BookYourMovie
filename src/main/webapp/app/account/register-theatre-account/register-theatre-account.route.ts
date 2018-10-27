import { Route } from '@angular/router';
import { RegisterTheatreAccountComponent } from './register-theatre-account.component';

export const registerTheatreRoute: Route = {
    path: 'register-theatre',
    component: RegisterTheatreAccountComponent,
    data: {
        authorities: [],
        pageTitle: 'Register Theatre Account'
    }
};
