export const enum SeatClass {
    LUXURY = 'LUXURY',
    GENERAL = 'GENERAL',
    BALCONY = 'BALCONY'
}

export interface ISeatType {
    id?: number;
    type?: SeatClass;
    price?: string;
    seatId?: number;
}

export class SeatType implements ISeatType {
    constructor(public id?: number, public type?: SeatClass, public price?: string, public seatId?: number) {}
}
