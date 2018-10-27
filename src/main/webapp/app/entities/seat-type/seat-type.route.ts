import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SeatType } from 'app/shared/model/seat-type.model';
import { SeatTypeService } from './seat-type.service';
import { SeatTypeComponent } from './seat-type.component';
import { SeatTypeDetailComponent } from './seat-type-detail.component';
import { SeatTypeUpdateComponent } from './seat-type-update.component';
import { SeatTypeDeletePopupComponent } from './seat-type-delete-dialog.component';
import { ISeatType } from 'app/shared/model/seat-type.model';

@Injectable({ providedIn: 'root' })
export class SeatTypeResolve implements Resolve<ISeatType> {
    constructor(private service: SeatTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((seatType: HttpResponse<SeatType>) => seatType.body));
        }
        return of(new SeatType());
    }
}

export const seatTypeRoute: Routes = [
    {
        path: 'seat-type',
        component: SeatTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SeatTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seat-type/:id/view',
        component: SeatTypeDetailComponent,
        resolve: {
            seatType: SeatTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SeatTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seat-type/new',
        component: SeatTypeUpdateComponent,
        resolve: {
            seatType: SeatTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SeatTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seat-type/:id/edit',
        component: SeatTypeUpdateComponent,
        resolve: {
            seatType: SeatTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SeatTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seatTypePopupRoute: Routes = [
    {
        path: 'seat-type/:id/delete',
        component: SeatTypeDeletePopupComponent,
        resolve: {
            seatType: SeatTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SeatTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
