import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRow } from 'app/shared/model/row.model';

@Component({
    selector: 'jhi-row-detail',
    templateUrl: './row-detail.component.html'
})
export class RowDetailComponent implements OnInit {
    row: IRow;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ row }) => {
            this.row = row;
        });
    }

    previousState() {
        window.history.back();
    }
}
