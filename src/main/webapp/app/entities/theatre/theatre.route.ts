import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Theatre } from 'app/shared/model/theatre.model';
import { TheatreService } from './theatre.service';
import { TheatreComponent } from './theatre.component';
import { TheatreDetailComponent } from './theatre-detail.component';
import { TheatreUpdateComponent } from './theatre-update.component';
import { TheatreDeletePopupComponent } from './theatre-delete-dialog.component';
import { ITheatre } from 'app/shared/model/theatre.model';

@Injectable({ providedIn: 'root' })
export class TheatreResolve implements Resolve<ITheatre> {
    constructor(private service: TheatreService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((theatre: HttpResponse<Theatre>) => theatre.body));
        }
        return of(new Theatre());
    }
}

export const theatreRoute: Routes = [
    {
        path: 'theatre',
        component: TheatreComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Theatres'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'theatre/:id/view',
        component: TheatreDetailComponent,
        resolve: {
            theatre: TheatreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Theatres'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'theatre/new',
        component: TheatreUpdateComponent,
        resolve: {
            theatre: TheatreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Theatres'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'theatre/:id/edit',
        component: TheatreUpdateComponent,
        resolve: {
            theatre: TheatreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Theatres'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const theatrePopupRoute: Routes = [
    {
        path: 'theatre/:id/delete',
        component: TheatreDeletePopupComponent,
        resolve: {
            theatre: TheatreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Theatres'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
