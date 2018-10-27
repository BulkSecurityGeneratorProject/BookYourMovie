import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITheatre } from 'app/shared/model/theatre.model';

@Component({
    selector: 'jhi-theatre-detail',
    templateUrl: './theatre-detail.component.html'
})
export class TheatreDetailComponent implements OnInit {
    theatre: ITheatre;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ theatre }) => {
            this.theatre = theatre;
        });
    }

    previousState() {
        window.history.back();
    }
}
