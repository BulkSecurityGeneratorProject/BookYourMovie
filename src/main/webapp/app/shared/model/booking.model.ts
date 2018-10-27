import { Moment } from 'moment';
import { ISeat } from 'app/shared/model//seat.model';

export interface IBooking {
    id?: number;
    time?: Moment;
    showId?: number;
    seats?: ISeat[];
}

export class Booking implements IBooking {
    constructor(public id?: number, public time?: Moment, public showId?: number, public seats?: ISeat[]) {}
}
