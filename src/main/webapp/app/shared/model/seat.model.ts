import { IRow } from 'app/shared/model//row.model';

export const enum Status {
    BOOKED = 'BOOKED',
    VACANT = 'VACANT',
    SPACE = 'SPACE'
}

export interface ISeat {
    id?: number;
    seatNumber?: number;
    status?: Status;
    bookingId?: number;
    screenId?: number;
    types?: IRow[];
}

export class Seat implements ISeat {
    constructor(
        public id?: number,
        public seatNumber?: number,
        public status?: Status,
        public bookingId?: number,
        public screenId?: number,
        public types?: IRow[]
    ) {}
}
