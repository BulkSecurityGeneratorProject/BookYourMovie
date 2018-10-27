import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISeat } from 'app/shared/model/seat.model';
import { SeatService } from './seat.service';
import { IBooking } from 'app/shared/model/booking.model';
import { BookingService } from 'app/entities/booking';
import { ISeatType } from 'app/shared/model/seat-type.model';
import { SeatTypeService } from 'app/entities/seat-type';

@Component({
    selector: 'jhi-seat-update',
    templateUrl: './seat-update.component.html'
})
export class SeatUpdateComponent implements OnInit {
    seat: ISeat;
    isSaving: boolean;

    bookings: IBooking[];

    seattypes: ISeatType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private seatService: SeatService,
        private bookingService: BookingService,
        private seatTypeService: SeatTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ seat }) => {
            this.seat = seat;
        });
        this.bookingService.query().subscribe(
            (res: HttpResponse<IBooking[]>) => {
                this.bookings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.seatTypeService.query().subscribe(
            (res: HttpResponse<ISeatType[]>) => {
                this.seattypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.seat.id !== undefined) {
            this.subscribeToSaveResponse(this.seatService.update(this.seat));
        } else {
            this.subscribeToSaveResponse(this.seatService.create(this.seat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISeat>>) {
        result.subscribe((res: HttpResponse<ISeat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBookingById(index: number, item: IBooking) {
        return item.id;
    }

    trackSeatTypeById(index: number, item: ISeatType) {
        return item.id;
    }
}
