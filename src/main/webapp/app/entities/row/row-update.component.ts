import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRow } from 'app/shared/model/row.model';
import { RowService } from './row.service';
import { ISeat } from 'app/shared/model/seat.model';
import { SeatService } from 'app/entities/seat';

@Component({
    selector: 'jhi-row-update',
    templateUrl: './row-update.component.html'
})
export class RowUpdateComponent implements OnInit {
    row: IRow;
    isSaving: boolean;

    seats: ISeat[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private rowService: RowService,
        private seatService: SeatService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ row }) => {
            this.row = row;
        });
        this.seatService.query().subscribe(
            (res: HttpResponse<ISeat[]>) => {
                this.seats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.row.id !== undefined) {
            this.subscribeToSaveResponse(this.rowService.update(this.row));
        } else {
            this.subscribeToSaveResponse(this.rowService.create(this.row));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRow>>) {
        result.subscribe((res: HttpResponse<IRow>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSeatById(index: number, item: ISeat) {
        return item.id;
    }
}
