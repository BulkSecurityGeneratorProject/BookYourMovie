import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISeatType } from 'app/shared/model/seat-type.model';

@Component({
    selector: 'jhi-seat-type-detail',
    templateUrl: './seat-type-detail.component.html'
})
export class SeatTypeDetailComponent implements OnInit {
    seatType: ISeatType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ seatType }) => {
            this.seatType = seatType;
        });
    }

    previousState() {
        window.history.back();
    }
}
