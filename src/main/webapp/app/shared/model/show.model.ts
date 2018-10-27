import { Moment } from 'moment';

export interface IShow {
    id?: number;
    time?: Moment;
    screenId?: number;
    movieId?: number;
}

export class Show implements IShow {
    constructor(public id?: number, public time?: Moment, public screenId?: number, public movieId?: number) {}
}
