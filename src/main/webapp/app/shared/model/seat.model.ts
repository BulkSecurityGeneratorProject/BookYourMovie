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
    seatTypeId?: number;
}

export class Seat implements ISeat {
    constructor(
        public id?: number,
        public seatNumber?: string,
        public status?: Status,
        public bookingId?: number,
        public seatTypeId?: number
    ) {}
}
