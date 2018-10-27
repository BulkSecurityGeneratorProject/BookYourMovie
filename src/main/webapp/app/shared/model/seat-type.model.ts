import { ISeat } from 'app/shared/model//seat.model';

export const enum SeatClass {
    LUXURY = 'LUXURY',
    GENERAL = 'GENERAL',
    BALCONY = 'BALCONY'
}

export interface ISeatType {
    id?: number;
    type?: SeatClass;
    price?: string;
    screenId?: number;
    seats?: ISeat[];
}

export class SeatType implements ISeatType {
    constructor(public id?: number, public type?: SeatClass, public price?: string, public screenId?: number, public seats?: ISeat[]) {}
}
