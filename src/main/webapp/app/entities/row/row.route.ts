import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Row } from 'app/shared/model/row.model';
import { RowService } from './row.service';
import { RowComponent } from './row.component';
import { RowDetailComponent } from './row-detail.component';
import { RowUpdateComponent } from './row-update.component';
import { RowDeletePopupComponent } from './row-delete-dialog.component';
import { IRow } from 'app/shared/model/row.model';

@Injectable({ providedIn: 'root' })
export class RowResolve implements Resolve<IRow> {
    constructor(private service: RowService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((row: HttpResponse<Row>) => row.body));
        }
        return of(new Row());
    }
}

export const rowRoute: Routes = [
    {
        path: 'row',
        component: RowComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rows'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'row/:id/view',
        component: RowDetailComponent,
        resolve: {
            row: RowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rows'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'row/new',
        component: RowUpdateComponent,
        resolve: {
            row: RowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rows'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'row/:id/edit',
        component: RowUpdateComponent,
        resolve: {
            row: RowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rows'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rowPopupRoute: Routes = [
    {
        path: 'row/:id/delete',
        component: RowDeletePopupComponent,
        resolve: {
            row: RowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rows'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
