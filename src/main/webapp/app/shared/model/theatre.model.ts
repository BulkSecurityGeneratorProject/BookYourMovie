import { IScreen } from 'app/shared/model//screen.model';

export interface ITheatre {
    id?: number;
    name?: string;
    area?: string;
    cityId?: number;
    ownerId?: number;
    screens?: IScreen[];
}

export class Theatre implements ITheatre {
    constructor(
        public id?: number,
        public name?: string,
        public area?: string,
        public cityId?: number,
        public ownerId?: number,
        public screens?: IScreen[]
    ) {}
}
