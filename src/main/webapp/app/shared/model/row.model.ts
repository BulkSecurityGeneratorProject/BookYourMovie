export interface IRow {
    id?: number;
    serialNumber?: number;
    startPos?: number;
    price?: string;
    name?: string;
    seatId?: number;
}

export class Row implements IRow {
    constructor(
        public id?: number,
        public serialNumber?: number,
        public startPos?: number,
        public price?: string,
        public name?: string,
        public seatId?: number
    ) {}
}
