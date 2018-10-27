import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShow } from 'app/shared/model/show.model';

type EntityResponseType = HttpResponse<IShow>;
type EntityArrayResponseType = HttpResponse<IShow[]>;

@Injectable({ providedIn: 'root' })
export class ShowService {
    public resourceUrl = SERVER_API_URL + 'api/shows';

    constructor(private http: HttpClient) {}

    create(show: IShow): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(show);
        return this.http
            .post<IShow>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(show: IShow): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(show);
        return this.http
            .put<IShow>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IShow>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IShow[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(show: IShow): IShow {
        const copy: IShow = Object.assign({}, show, {
            time: show.time != null && show.time.isValid() ? show.time.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.time = res.body.time != null ? moment(res.body.time) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((show: IShow) => {
            show.time = show.time != null ? moment(show.time) : null;
        });
        return res;
    }
}
