import { ISeatType } from 'app/shared/model//seat-type.model';

export const enum Status {
    BOOKED = 'BOOKED',
    VACANT = 'VACANT',
    SPACE = 'SPACE'
}

export interface ISeat {
    id?: number;
    seatNumber?: string;
    status?: Status;
    bookingId?: number;
    screenId?: number;
    types?: ISeatType[];
}

export class Seat implements ISeat {
    constructor(
        public id?: number,
        public seatNumber?: string,
        public status?: Status,
        public bookingId?: number,
        public screenId?: number,
        public types?: ISeatType[]
    ) {}
}
