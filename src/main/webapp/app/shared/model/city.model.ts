import { ITheatre } from 'app/shared/model//theatre.model';

export const enum CityNames {
    HYDERABAD = 'HYDERABAD',
    BANGALORE = 'BANGALORE',
    KOLKATTA = 'KOLKATTA'
}

export interface ICity {
    id?: number;
    name?: CityNames;
    theatres?: ITheatre[];
}

export class City implements ICity {
    constructor(public id?: number, public name?: CityNames, public theatres?: ITheatre[]) {}
}
