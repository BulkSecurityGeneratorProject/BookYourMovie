import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShow } from 'app/shared/model/show.model';

@Component({
    selector: 'jhi-show-detail',
    templateUrl: './show-detail.component.html'
})
export class ShowDetailComponent implements OnInit {
    show: IShow;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ show }) => {
            this.show = show;
        });
    }

    previousState() {
        window.history.back();
    }
}
