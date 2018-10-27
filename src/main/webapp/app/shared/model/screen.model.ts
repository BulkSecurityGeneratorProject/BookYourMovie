import { ISeatType } from 'app/shared/model//seat-type.model';
import { IShow } from 'app/shared/model//show.model';

export interface IScreen {
    id?: number;
    name?: string;
    theatreId?: number;
    seatTypes?: ISeatType[];
    shows?: IShow[];
}

export class Screen implements IScreen {
    constructor(
        public id?: number,
        public name?: string,
        public theatreId?: number,
        public seatTypes?: ISeatType[],
        public shows?: IShow[]
    ) {}
}
