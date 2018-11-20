import { Route } from '@angular/router';
import { OwnerComponent } from './owner.component';
import { UserRouteAccessService } from 'app/core';
import { screenRoute } from '../screen';

const defaultRoute: Route = {
    path: '',
    redirectTo: 'screen',
    pathMatch: 'full'
};

const routes: Route[] = [defaultRoute, ...screenRoute];

export const ownerRoute: Route = {
    path: 'owner',
    component: OwnerComponent,
    data: {
        authorities: ['ROLE_OWNER'],
        pageTitle: 'Theatre Owner Home'
    },
    children: routes,
    canActivate: [UserRouteAccessService]
};
