import { Route } from '@angular/router';
import { OwnerComponent } from './owner.component';
import {UserRouteAccessService} from 'app/core';

export const ownerRoute: Route = {
    path: 'owner',
    component: OwnerComponent,
    data: {
        authorities: ['ROLE_OWNER'],
        pageTitle: 'Theatre Owner Home'
    },
    canActivate: [UserRouteAccessService]
};
