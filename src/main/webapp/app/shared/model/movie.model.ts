export const enum Language {
    TELUGU = 'TELUGU',
    HINDI = 'HINDI',
    ENGLISH = 'ENGLISH',
    TAMIL = 'TAMIL',
    MALAYALAM = 'MALAYALAM',
    KANNADA = 'KANNADA'
}

export interface IMovie {
    id?: number;
    name?: string;
    language?: Language;
    description?: string;
    imageUrl?: string;
}

export class Movie implements IMovie {
    constructor(
        public id?: number,
        public name?: string,
        public language?: Language,
        public description?: string,
        public imageUrl?: string
    ) {}
}
