import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Show } from 'app/shared/model/show.model';
import { ShowService } from './show.service';
import { ShowComponent } from './show.component';
import { ShowDetailComponent } from './show-detail.component';
import { ShowUpdateComponent } from './show-update.component';
import { ShowDeletePopupComponent } from './show-delete-dialog.component';
import { IShow } from 'app/shared/model/show.model';

@Injectable({ providedIn: 'root' })
export class ShowResolve implements Resolve<IShow> {
    constructor(private service: ShowService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((show: HttpResponse<Show>) => show.body));
        }
        return of(new Show());
    }
}

export const showRoute: Routes = [
    {
        path: 'show',
        component: ShowComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shows'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'show/:id/view',
        component: ShowDetailComponent,
        resolve: {
            show: ShowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shows'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'show/new',
        component: ShowUpdateComponent,
        resolve: {
            show: ShowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shows'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'show/:id/edit',
        component: ShowUpdateComponent,
        resolve: {
            show: ShowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shows'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const showPopupRoute: Routes = [
    {
        path: 'show/:id/delete',
        component: ShowDeletePopupComponent,
        resolve: {
            show: ShowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shows'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
