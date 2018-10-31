import { ISeat } from 'app/shared/model//seat.model';
import { IShow } from 'app/shared/model//show.model';

export interface IScreen {
    id?: number;
    name?: string;
    theatreId?: number;
    seats?: ISeat[];
    shows?: IShow[];
}

export class Screen implements IScreen {
    constructor(public id?: number, public name?: string, public theatreId?: number, public seats?: ISeat[], public shows?: IShow[]) {}
}
